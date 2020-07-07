package entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.AccessType;

/*
 * * This is the persistence POJO (Plain Ordinary Java Object) mapping to the database table "employees"
 * using Hibernate 3 Annotations.
 * 
 * Studio Object Entity
 * @author Crystal Hansen
 *  May 29 2020
 * *
 * */

@Entity						// This is a persistent class
@Table(name="studio")	// This class maps to the table named "employees".
@AccessType("field")		// This class uses field-based annotations.
public class Studio implements java.io.Serializable{

	@Transient 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )	
	private int studioId;
	
	@Column(length=50)
	private String studioName;
	@Column(length=50)
	private String studioDescription;
	
	@Column(length=50)
	private String studioSizeSq;
	@Column(length=50)
	private String studioAvailability;
	@Column(length=50)
	private String studioAccessories;

	private BigDecimal studioCharge;
	
	private String fullimage;
	private String studiofolder;
	
	public Studio() {
		super();
	}
	
	public Studio(int studioId) {
		this.studioId = studioId;
	}
	
	
	
//	public Studio(String studioName,
//			String studioDescription, 
//			String studioSizeSq, 
//			String studioAvailability, 
//			String studioAccessories,
//			BigDecimal studioCharge) {
//		super();
//		this.studioName = studioName;
//		this.studioDescription = studioDescription;
//		this.studioSizeSq = studioSizeSq;
//		this.studioAvailability = studioAvailability;
//		this.studioAccessories = studioAccessories;
//		this.studioCharge = studioCharge;
//	}
	
	public Studio(int studioId, String studioName, String studioDescription, String studioSizeSq,
			String studioAvailability, String studioAccessories, BigDecimal studioCharge, String fullimage,
			String studiofolder) {
		super();
		this.studioId = studioId;
		this.studioName = studioName;
		this.studioDescription = studioDescription;
		this.studioSizeSq = studioSizeSq;
		this.studioAvailability = studioAvailability;
		this.studioAccessories = studioAccessories;
		this.studioCharge = studioCharge;
		this.fullimage = fullimage;
		this.studiofolder = studiofolder;
	}

	public String getStudioName() {
		return studioName;
	}
	public void setStudioName(String studioName) {
		this.studioName = studioName;
	}
	
	
	public int getStudioId() {
		return studioId;
	}

	public void setStudioId(int studioId) {
		this.studioId = studioId;
	}

	public String getStudioDescription() {
		return studioDescription;
	}

	public void setStudioDescription(String studioDescription) {
		this.studioDescription = studioDescription;
	}

	public String getStudioSizeSq() {
		return studioSizeSq;
	}
	public void setStudioSizeSq(String studioSizeSq) {
		this.studioSizeSq = studioSizeSq;
	}
	public String getStudioAvailability() {
		return studioAvailability;
	}
	public void setStudioAvailability(String studioAvailability) {
		this.studioAvailability = studioAvailability;
	}
	public String getStudioAccessories() {
		return studioAccessories;
	}
	public void setStudioAccessories(String studioAccessories) {
		this.studioAccessories = studioAccessories;
	}
	public BigDecimal getStudioCharge() {
		return studioCharge;
	}
	public void setStudioCharge(BigDecimal studioCharge) {
		this.studioCharge = studioCharge;
	}

	public String getFullimage() {
		return fullimage;
	}

	public void setFullimage(String fullimage) {
		this.fullimage = fullimage;
	}

	public String getStudiofolder() {
		return studiofolder;
	}

	public void setStudiofolder(String studiofolder) {
		this.studiofolder = studiofolder;
	}
	
	
	
	
}
