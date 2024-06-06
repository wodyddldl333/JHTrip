package com.ssafy.jhtrip.map.dto;

public class MapDto {

	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String tel;
	private String firstImage;
	private float latitude;
	private float longitude;
	private int sidoCode;
	private int gugunCode;
	private String overView;

	public MapDto() {}

	public MapDto(int contentId, int contentTypeId, String title, String addr1, String tel, String firstImage, float latitude, float longitude, int sidoCode, int gugunCode, String overView) {
		this.contentId = contentId;
		this.contentTypeId = contentTypeId;
		this.title = title;
		this.addr1 = addr1;
		this.tel = tel;
		this.firstImage = firstImage;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sidoCode = sidoCode;
		this.gugunCode = gugunCode;
		this.overView = overView;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public int getGugunCode() {
		return gugunCode;
	}

	public void setGugunCode(int gugunCode) {
		this.gugunCode = gugunCode;
	}

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	@Override
	public String toString() {
		return "MapDto{" +
				"contentId=" + contentId +
				", contentTypeId=" + contentTypeId +
				", title='" + title + '\'' +
				", addr1='" + addr1 + '\'' +
				", tel='" + tel + '\'' +
				", firstImage='" + firstImage + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", sidoCode=" + sidoCode +
				", gugunCode=" + gugunCode +
				", overView='" + overView + '\'' +
				'}';
	}
}
