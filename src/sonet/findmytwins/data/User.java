package sonet.findmytwins.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="FMT_USER")
public class User implements Serializable{

	public static final int PROFILE_FB=1;
	public User() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	
	String email;
	@Column(unique=true)
	String login;
	@Column
	String name;
	@Column
	String pwd;
	@Column
	String surname;
	@Column
	String birthday;
	
	@Column
	String sex;
	@Column
	String onlineStatus;

	@Column
	String country;
	
	@Column
	String profileLink;
	
	@Column
	String pic;
	
	@Column
	String sonetUserId;
	
	@Column
	int profileType;
	
	
	
	@Column(nullable=false)
	int step=0;
	
	@Column
	@Temporal(value=TemporalType.TIMESTAMP)
	Date testDate;
	
	@Column
	@Temporal(value=TemporalType.TIMESTAMP)
	Date lastLogin;
	
	@Column
	@OneToMany(mappedBy="user")
	Set<UserAnswers> userAnswers;
	
	@Column
	@OneToMany(mappedBy="user")
//	@JoinColumn(name="user_id")
	Set<Twins> twins;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Set<UserAnswers> getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(Set<UserAnswers> userAnswers) {
		this.userAnswers = userAnswers;
	}
	
	public Set<Twins> getTwins() {
		return twins;
	}
	public void setTwins(Set<Twins> twins) {
		this.twins = twins;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getProfileLink() {
		return profileLink;
	}
	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public int getProfileType() {
		return profileType;
	}
	public void setProfileType(int profileType) {
		this.profileType = profileType;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSonetUserId() {
		return sonetUserId;
	}
	public void setSonetUserId(String sonetUserId) {
		this.sonetUserId = sonetUserId;
	}
	
	
}
