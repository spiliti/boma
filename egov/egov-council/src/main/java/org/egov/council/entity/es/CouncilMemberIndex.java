package org.egov.council.entity.es;

import static org.egov.infra.utils.ApplicationConstant.DEFAULT_TIMEZONE;
import static org.egov.infra.utils.ApplicationConstant.ES_DATE_FORMAT;

import java.util.Date;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(indexName = "councilmember", type = "councilmember")
public class CouncilMemberIndex {
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String electionWard;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String designation;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String qualification;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String caste;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String partyAffiliation;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String gender;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
    private Date birthDate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
    private Date oathDate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ES_DATE_FORMAT, timezone = DEFAULT_TIMEZONE)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time, pattern = ES_DATE_FORMAT)
    private Date electionDate;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String mobileNumber;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String emailId;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String status;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElectionWard() {
        return electionWard;
    }

    public void setElectionWard(String electionWard) {
        this.electionWard = electionWard;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getPartyAffiliation() {
        return partyAffiliation;
    }

    public void setPartyAffiliation(String partyAffiliation) {
        this.partyAffiliation = partyAffiliation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getOathDate() {
        return oathDate;
    }

    public void setOathDate(Date oathDate) {
        this.oathDate = oathDate;
    }

    public Date getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(Date electionDate) {
        this.electionDate = electionDate;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
   
}
