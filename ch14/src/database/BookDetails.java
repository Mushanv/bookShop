package database;

public class BookDetails implements Comparable {
    private String bookId = null;		//book ID
    private String title = null;    	//����
    private String name = null;			//��������
    private float price = 0.0F;			//�۸�
    private boolean onSale = false;		//ָʾ���������ϼ�
    private int year = 0;				//����ʱ��
    private String description = null;	//����
    private int inventory = 0;			//���

   //��ʼ����Ա����
    public BookDetails(String bookId, String name,
        String title, float price, boolean onSale, int year,
        String description, int inventory) {
        this.bookId = bookId;
        this.title = title;        
        this.name = name;
        this.price = price;
        this.onSale = onSale;
        this.year = year;
        this.description = description;
        this.inventory = inventory;
    }

    public String getBookId() {
        return this.bookId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public boolean getOnSale() {
        return this.onSale;
    }

    public int getYear() {
        return this.year;
    }

    public String getDescription() {
        return this.description;
    }

    public int getInventory() {
        return this.inventory;
    }

    public void setBookId(String id) {
        this.bookId = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int compareTo(Object o) {
        BookDetails n = (BookDetails) o;
        int lastCmp = title.compareTo(n.title);

        return (lastCmp);
    }
}
