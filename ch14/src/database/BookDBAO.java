package database;

import java.sql.*;

import config.Constants;

import java.util.*;
import exception.*;
import cart.*;


public class BookDBAO {
    private ArrayList books;
    Connection con;
    private boolean conFree = true;
    
    /*
     * ��ʼ����MySQL���ݿ������
     */
    public BookDBAO() throws Exception {
    	try {
            Class.forName(Constants.dbdriver).newInstance();
            con=DriverManager.getConnection(Constants.dburl);          
           
        } catch (Exception ex) {
            throw new Exception("Couldn't open connection to database: " +
                ex.getMessage());
        }
    }

    /*
     * �ر������ݿ������
     */
    public void remove() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * ��ȡConnection����
     */
    protected synchronized Connection getConnection() {
        while (conFree == false) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        conFree = false;
        notify();

        return con;
    }
    /*
     * ��ȡһ�����е�Connection����
     */
    protected synchronized void releaseConnection() {
        while (conFree == true) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        conFree = true;
        notify();
    }
    
    /*
     * ��ȡ������Ŀ�б�
     */
    public List getBooks() throws BooksNotFoundException {
        books = new ArrayList();

        try {
        	String selectStatement = "select id,name,title,price,onSale,year,description,inventory " +
        "from books";
            getConnection();

            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                BookDetails bd =
                	new BookDetails(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getFloat(4),
                            rs.getBoolean(5), rs.getInt(6), rs.getString(7),
                            rs.getInt(8));
                if (rs.getInt(8) > 0) {
                    books.add(bd);
                }
            }

            prepStmt.close();
        } catch (SQLException ex) {
            throw new BooksNotFoundException(ex.getMessage());
        }

        releaseConnection();
        Collections.sort(books);

        return books;
    }

    /*
     * ��ȡ�ؼ���bookId����Ŀ����ϸ��Ϣ
     */
    public BookDetails getBookDetails(String bookId)
        throws BookNotFoundException {
        try {
        	String selectStatement = "select id,name,title,price,onSale,year,description,inventory " + "from books where id = ? ";
            getConnection();

            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, bookId);

            ResultSet rs = prepStmt.executeQuery();

            if (rs.next()) {
                BookDetails bd =
                	new BookDetails(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getFloat(4),
                             rs.getBoolean(5), rs.getInt(6), rs.getString(7),
                             rs.getInt(8));
                prepStmt.close();
                releaseConnection();

                return bd;
            } else {
                prepStmt.close();
                releaseConnection();
                throw new BookNotFoundException("Couldn't find book: " +
                    bookId);
            }
        } catch (SQLException ex) {
            releaseConnection();
            throw new BookNotFoundException("Couldn't find book: " + bookId +
                " " + ex.getMessage());
        }
    }

    /*
     * ������ڹ��ﳵ�е�������Ŀ���������������ݿ�
     */
    public void buyBooks(ShoppingCart cart) throws OrderException {
        Collection items = cart.getItems();
        Iterator i = items.iterator();

        try {
            getConnection();
            con.setAutoCommit(false);

            while (i.hasNext()) {
                ShoppingCartItem sci = (ShoppingCartItem) i.next();
                BookDetails bd = (BookDetails) sci.getItem();
                String id = bd.getBookId();
                int quantity = sci.getQuantity();
                buyBook(id, quantity);
            }

            con.commit();
            con.setAutoCommit(true);
            releaseConnection();
        } catch (Exception ex) {
            try {
                con.rollback();
                releaseConnection();
                throw new OrderException("Transaction failed: " +
                    ex.getMessage());
            } catch (SQLException sqx) {
                releaseConnection();
                throw new OrderException("Rollback failed: " +
                    sqx.getMessage());
            }
        }
    }

    /*
     * ����quantity���ؼ���ΪbooId����
     */
    public void buyBook(String bookId, int quantity) throws OrderException {
        try {
        	String selectStatement = "select id,name,title,price,onSale,year,description,inventory " + "from books where id = ? ";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, bookId);

            ResultSet rs = prepStmt.executeQuery();

            if (rs.next()) {
                int inventory = rs.getInt(8);
                prepStmt.close();

                if ((inventory - quantity) >= 0) {
                    String updateStatement =
                        "update books set inventory = inventory - ? where id = ?";
                    prepStmt = con.prepareStatement(updateStatement);
                    prepStmt.setInt(1, quantity);
                    prepStmt.setString(2, bookId);
                    prepStmt.executeUpdate();
                    prepStmt.close();
                } else {
                    throw new OrderException("Not enough of " + bookId +
                        " in stock to complete order.");
                }
            }
        } catch (Exception ex) {
            throw new OrderException("Couldn't purchase book: " + bookId +
                ex.getMessage());
        }
    }
}
