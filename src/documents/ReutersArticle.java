package documents;

public class ReutersArticle extends AbstractDocument {
	
	private String title;
	
	public ReutersArticle(int article_id, String title, String body) {
		super(article_id, body);
		this.title = title;		
	}
	
	@Override
	public String getAllText() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.title + " ");
		sb.append(super.getBody());
		return sb.toString();
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
