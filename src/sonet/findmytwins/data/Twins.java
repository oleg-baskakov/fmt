package sonet.findmytwins.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;

@Entity
public class Twins {


	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	long id;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@Index(name="indx_twin_user")
	User user;
	
	@ManyToOne
	@JoinColumn(name="twin_id")
	User twin;
	
	@Column
	@Index(name="indx_twin_twin_power")
	int twinPower;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getTwin() {
		return twin;
	}

	public void setTwin(User twin) {
		this.twin = twin;
	}

	public int getTwinPower() {
		return twinPower;
	}

	public void setTwinPower(int twinPower) {
		this.twinPower = twinPower;
	}
	
}
