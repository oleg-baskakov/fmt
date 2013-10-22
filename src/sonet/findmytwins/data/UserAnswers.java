package sonet.findmytwins.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UserAnswers {

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	long id;
	
	
	@Column
	byte number;
	
	
	@ManyToOne
	@JoinColumn(name="useranswer_id")
	User user;

	public UserAnswers() {
		// TODO Auto-generated constructor stub
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte getNumber() {
		return number;
	}

	public void setNumber(byte number) {
		this.number = number;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	
}
