package com.priester.crawler.score;

public class SpecialtyScoreDetail {
	
	private String province;
	private String ArtSci;
	private String universities;
	private String specialtyName;
	private String year;
	private String highestScore;
	private String averageScore;
	private String minimumScore;
	private String type;
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getArtSci() {
		return ArtSci;
	}
	public void setArtSci(String artSci) {
		ArtSci = artSci;
	}
	public String getUniversities() {
		return universities;
	}
	public void setUniversities(String universities) {
		this.universities = universities;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(String highestScore) {
		this.highestScore = highestScore;
	}
	public String getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
	}
	public String getMinimumScore() {
		return minimumScore;
	}
	public void setMinimumScore(String minimumScore) {
		this.minimumScore = minimumScore;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SpecialtyScoreDetail [province=" + province + ", ArtSci=" + ArtSci + ", universities=" + universities
				+ ", specialtyName=" + specialtyName + ", year=" + year + ", highestScore=" + highestScore
				+ ", averageScore=" + averageScore + ", minimumScore=" + minimumScore + ", type=" + type + "]";
	}
	
	
	
}
