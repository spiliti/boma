/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */
package org.egov.ptis.constants;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.AUGUST;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;
import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.OCTOBER;
import static java.util.Calendar.SEPTEMBER;
import static org.egov.collection.constants.CollectionConstants.COLLECTION_TYPE_COUNTER;
import static org.egov.collection.constants.CollectionConstants.COLLECTION_TYPE_FIELDCOLLECTION;
import static org.egov.collection.constants.CollectionConstants.COLLECTION_TYPE_ONLINECOLLECTION;
import static org.egov.ptis.constants.PropertyTaxConstants.NATURE_OF_WORK_GRP;
import static org.egov.ptis.constants.PropertyTaxConstants.NATURE_OF_WORK_RP;
import static org.egov.ptis.constants.PropertyTaxConstants.NATURE_REVISION_PETITION;
import static org.egov.ptis.constants.PropertyTaxConstants.WFLOW_ACTION_APPEALPETITION;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public interface PropertyTaxConstants {

    // take a list of needed things
    public static final String APPCONFIG_ISCORPORATION = "IS_CORPORATION";
    public static final String APPCONFIG_ISSEASHORE_ULB = "IS_SEASHORE_ULB";
    public static final String APPCONFIG_IS_PRIMARY_SERVICECHARGES_APPLICABLE = "IS_PRIMARYSERVICECHARGES_APPLICABLE";
    public static final String APPCONFIG_CLOSED_MUTATION_RECEIPT = "CANCEL_RECEIPT_FOR_CLOSED_MUTATION";
    public static final String EDIT_COLL_FOR_CURRYEAR = "EDIT_COLLECTION_FOR_CURRENTYEAR";
    public static final Float SQUARE_YARD_TO_SQUARE_METER_VALUE = 0.836127f;

    // General constants used across Clients
    public static final String PTMODULENAME = "Property Tax";
    public static final String DEACTIVATION = "DEACTIVATION";
    public static final String DATE_FORMAT_DDMMYYY = "dd/MM/yyyy";
    public static final String PROP_ADDR_TYPE = "PROPERTY";

    // Workflow statuses
    public static final String PROPERTY_STATUS_WORKFLOW = "WORKFLOW";
    public static final String PROPERTY_STATUS_APPROVEL_PENDING = "APPROVAL PENDING";
    public static final String PROPERTY_STATUS_APPROVED = "APPROVED";

    // Demand Category Constants used across Clients
    public static final String REASON_CATEGORY_CODE_TAX = "TAX";
    public static final String REASON_CATEGORY_CODE_FINES = "FINES";

    // Demand Reason Constants used across Clients
    public static final String ADVANCE_DMD_RSN_CODE = "ADVANCE";
    public static final String PENALTY_DMD_RSN_CODE = "PENALTY";

    // Others
    public static final String CURR_DMD_STR = "CURR_DMD";
    public static final String ARR_DMD_STR = "ARR_DMD";
    public static final String CURR_BAL_STR = "CURR_BAL";
    public static final String CURR_COLL_STR = "CURR_COLL";
    public static final String ARR_COLL_STR = "ARR_COLL";
    public static final String ARR_BAL_STR = "ARR_BAL";
    public static final String CURR_PENALTY_DMD_STR = "CURR_PEANLTY_DMD";
    public static final String ARR_PENALTY_DMD_STR = "ARR_PENALTY_DMD";
    public static final String CURR_PENALTY_COLL_STR = "CURR_PENALTY_COLL";
    public static final String ARR_PENALTY_COLL_STR = "ARR_PENALTY_COLL";
    public static final String CURRENT_REBATE_STR = "CURRENT_REBATE";
    public static final String ARREAR_REBATE_STR = "ARREAR_REBATE";
    public static final String CURR_FIRSTHALF_DMD_STR = "CURR_FIRSTHALF_DMD";
    public static final String CURR_FIRSTHALF_COLL_STR = "CURR_FIRSTHALF_COLL";
    public static final String CURR_FIRSTHALF_PENALTY_DMD_STR = "CURR_FIRSTHALF_PENALTY_DMD";
    public static final String CURR_FIRSTHALF_PENALTY_COLL_STR = "CURR_FIRSTHALF_PENALTY_COLL";
    public static final String CURR_SECONDHALF_PENALTY_DMD_STR = "CURR_SECONDHALF_PENALTY_DMD";
    public static final String CURR_SECONDHALF_PENALTY_COLL_STR = "CURR_SECONDHALF_PENALTY_COLL";
    public static final String CURR_SECONDHALF_DMD_STR = "CURR_SECONDHALF_DMD";
    public static final String CURR_SECONDHALF_COLL_STR = "CURR_SECONDHALF_COLL";
    public static final String ADVANCE_COLLECTION_STR = "ADVANCECOLLECTION";

    public static final String BUILT_UP_PROPERTY = "BuiltUpProperty";
    public static final String VACANT_PROPERTY = "VacantProperty";
    public static final String APARTMENT_PROPERTY = "Apartment";

    public static final String APPCONFIG_PENALTY_WAIVER_ENABLED = "PENALTY_WAIVER_ENABLED";
    public static final String APPCONFIG_PENALTY_WAIVER_CUTOFF_DATE = "PENALTY_WAIVER_CUTOFF_DATE";

    // objection status CODE values
    public static final String OBJECTION_MODULE = "PTPetition";
    public static final String OBJECTION_CREATED = "CREATED";
    public static final String OBJECTION_HEARING_FIXED = "HEARING DATE FIXED";
    public static final String OBJECTION_HEARING_COMPLETED = "HEARING COMPLETED";
    public static final String REVISIONPETITION_CREATED = "Revision Petition:CREATED";
    public static final String OBJECTION_INSPECTION_COMPLETED = "INSPECTION COMPLETED";
    public static final String OBJECTION_INSPECTION_VERIFY = "INSPECTION VERIFY";
    public static final String OBJECTION_ACCEPTED = "OBJECTION ACCEPTED";
    public static final String OBJECTION_REJECTED = "OBJECTION REJECTED";
    public static final String OBJECTION_RECORD_GENERATEHEARINGNOTICE = "GENERATE HEARING NOTICE";
    public static final String OBJECTION_RECORD_INSPECTIONDETAILS = "Record inspection details";
    public static final String OBJECTION_PRINT_ENDORSEMENT = "Print Endoresement";
    public static final String CANCELLED = "CANCELLED";

    public static final String NOTICE_TYPE_GRPPROCEEDINGS = "GRP Proceedings";
    public static final String GRP_RP_HEARING_DATE_FIXED = "fixed";
    public static final String PROPERTY_MODIFY_REASON_REVISION_PETITION = "RP";
    public static final String REVISION_PETITION = "REVISION PETITION";

    // New RP Changes
    public static final String RP_HEARINGCOMPLETED = "RP:Hearing completed";
    public static final String RP_INSPECTIONVERIFIED = "RP:Inspection verified";
    public static final String RP_INSPECTION_COMPLETE = "RP:Inspection completed";
    public static final String RP_WF_REGISTERED = "RP:Registered";
    public static final String RP_CREATED = "RP:CREATED";

    // New GRP Changes
    public static final String GRP_HEARINGCOMPLETED = "GRP:Hearing completed";
    public static final String GRP_INSPECTIONVERIFIED = "GRP:Inspection verified";
    public static final String GRP_INSPECTION_COMPLETE = "GRP:Inspection completed";
    public static final String GRP_WF_REGISTERED = "GRP:Registered";
    public static final String RP_NEW = "RP:NEW";
    public static final String GRP_NEW = "GRP:NEW";
    public static final String GRP_CREATED = "GRP:CREATED";
    public static final String NATURE_OF_WORK_GRP = "GRP";
    public static final String NATURE_OF_WORK_RP = "RP";
    public static final String GRP_STATUS_CODE = "GRP";

    public static final String RECOVERY_MODULE = "PTRecovery";
    public static final String RECOVERY_NOTICE155CREATED = "NOTICE155CREATED";
    public static final String RECOVERY_NOTICE155GENERATIONPENDING = "NOTICE155GENERATIONPENDING";
    public static final String RECOVERY_NOTICE155GENERATED = "NOTICE155GENERATED";
    public static final String RECOVERY_WARRANTPREPARED = "WARRANT APPLICATION PREPARED";
    public static final String RECOVERY_WARRANTAPPROVED = "WARRANT APPLICATION APPROVED";
    public static final String RECOVERY_WARRANTNOTICECREATED = "WARRANT NOTICE CREATED";
    public static final String RECOVERY_WARRANTNOTICEISSUED = "WARRANT NOTICE ISSUED";
    public static final String RECOVERY_CEASENOTICECREATED = "CEASE NOTICE CREATED";
    public static final String RECOVERY_CEASENOTICEISSUED = "CEASE NOTICE ISSUED";

    // GIS Search Property
    public static final String SRCH_BOUNDARY_ID = "BOUNDARYID";
    public static final String SRCH_OWNER_NAME = "OWNERNAME";
    public static final String SRCH_NEW_HOUSE_NO = "NEWHOUSENO";
    public static final String SRCH_OLD_HOUSE_NO = "OLDHOUSENO";
    public static final String SRCH_PROPERTY_TYPE = "PROPERTYTYPE";
    public static final String SRCH_DEMAND_FROM_AMOUNT = "DMD_FROM_AMOUNT";
    public static final String SRCH_DEMAND_TO_AMOUNT = "DMD_TO_AMOUNT";
    public static final String SRCH_DEFAULTER_FROM_AMOUNT = "DEFAULTER_FROM_AMOUNT";
    public static final String SRCH_DEFAULTER_TO_AMOUNT = "DEFAULTER_TO_AMOUNT";

    // Mutation Reason
    public static final String MUTATION_REASON_CODE_GIFT = "GIFTDEED";
    public static final String MUTATION_REASON_CODE_WILL = "WILLDEED";
    public static final String MUTATION_REASON_CODE_SALE = "SALEDEED";
    public static final String MUTATION_REASON_CODE_RENT = "RENTDEED";
    public static final String MUTATION_REASON_CODE_UNREGISTEREDWILL = "UNREGWILLDEED";
    public static final String MUTATION_REASON_CODE_REGISTERED = "REGISTERED";
    public static final String MUTATION_REASON_CODE_SUCCESSION = "SUCCESSION";
    public static final String MUTATION_REASON_CODE_TITLEDEED = "TITLEDEED";
    public static final String MUTATION_REASON_CODE_PARTISION = "PARTISION";
    public static final String MUTATION_REASON_CODE_RELINQUISH = "RELINQUISH";
    public static final String MUTATION_REASON_CODE_PARTITION = "PARTITIOND";

    // Property status values
    public static final Character STATUS_ISHISTORY = 'H';
    public static final Character STATUS_ISACTIVE = 'A';
    public static final Character STATUS_WORKFLOW = 'W';
    public static final Character STATUS_OBJECTED = 'O';
    public static final Character STATUS_DEMAND_INACTIVE = 'I';
    public static final Character STATUS_CANCELLED = 'C';

    // Bill types
    public static final String BILLTYPE_MANUAL = "MANUAL";
    public static final String BILLTYPE_AUTO = "AUTO";
    public static final String BILLTYPE_ONLINE = "ONLINE";

    public static final Character CANCELLED_RECEIPT_STATUS = 'C';

    // PTIS MODULEID IN EG_MODULES
    public static final String PTIS_EG_MODULES_ID = "2";

    // Demand Status when Cheque bounced/cheque amount paid
    public static final Character DMD_STATUS_CHEQUE_BOUNCED = 'B';
    public static final Character DMD_STATUS_CHQ_BOUNCE_AMOUNT_PAID = 'P';
    public static final Character DMD_STATUS_NO_CHQ_BOUNCED = 'N';

    // Named Queries
    public static final String QUERY_STATUS_BY_MODULE_AND_CODE = "getStatusByModuleAndCode";
    public static final String QUERY_PROP_STATUS_BY_STATUSCODE = "getPropStatusByStatusCode";

    public static final String STRING_NOT_AVAILABLE = "N/A";

    public static final String PTIS_COLLECTION_SERVICE_CODE = "PT";

    public static final String STATUS_OBJECTED_STR = "OBJECTED";

    public static final String STR_YES = "Yes";

    public static final String MUTATION_FEE_STR = "MUTATION FEE";

    public static final String PROP_SOURCE = "MNCPL-RECORDS";

    // hierarchy types
    public static final String ELECTION_HIERARCHY_TYPE = "ELECTION";
    public static final String ADMIN_HIERARCHY_TYPE = "ADMINISTRATION";
    public static final String LOCATION_HIERARCHY_TYPE = "LOCATION";
    public static final String REVENUE_HIERARCHY_TYPE = "REVENUE";

    // boundary Types
    public static final String CITY_BNDRY_TYPE = "Revenue City";
    public static final String ZONE_BNDRY_TYPE = "Revenue Zone";
    public static final String WARD_BNDRY_TYPE = "Revenue Ward";
    public static final String AREA_BNDRY_TYPE = "Revenue Area";
    public static final String ZONE = "Zone";
    public static final String WARD = "Ward";
    public static final String BLOCK = "Block";
    public static final String STREET = "Street";
    public static final String LOCALITY = "locality";
    public static final String ELECTIONWARD_BNDRY_TYPE = "Election Ward";
    public static final String LOCALITY_BNDRY_TYPE = "Locality";

    // Ownership type code
    public static final String OWNERSHIP_TYPE_VAC_LAND = "VAC_LAND";
    public static final String OWNERSHIP_TYPE_PRIVATE = "PRIVATE";
    public static final String OWNERSHIP_TYPE_STATE_GOVT = "STATE_GOVT";
    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT_333 = "CENTRAL_GOVT_33.33";
    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT_50 = "CENTRAL_GOVT_50";
    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT_75 = "CENTRAL_GOVT_75";
    public static final String OWNERSHIP_TYPE_EWSHS = "EWSHS";

    // Ownership type string
    public static final String OWNERSHIP_TYPE_VAC_LAND_STR = "Vacant Land";
    public static final String OWNERSHIP_TYPE_PRIVATE_STR = "Private";
    public static final String OWNERSHIP_TYPE_STATE_GOVT_STR = "State Government";
    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT_333_STR = "Central Government 33.33%";
    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT_50_STR = "Central Government 50%";
    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT_75_STR = "Central Government 75%";

    // Property Type Categories
    public static final String PROPTYPE_VAC_LAND = "RESD";
    public static final String PROPTYPE_RESD = "RESD";
    public static final String PROPTYPE_NON_RESD = "NON_RESD";
    public static final String PROPTYPE_MIXED = "MIXED";

    // Property Types for Usage
    public static final String USAGES_FOR_RESD = "C1, D1, D2, E1, E2, E3";
    public static final String USAGES_FOR_NON_RESD = "A1, A2, A3, A4, A5, A6, B1, B2, C2, C3, C4";
    public static final String USAGES_FOR_OPENPLOT = "C5, D3, E4";

    // Notice Types
    public static final String NOTICE127 = "Notice 127";
    public static final String NOTICE134 = "Notice 134";
    public static final String NOTICE125 = "Notice125";
    public static final String NOTICE_PRATIVRUTTA = "Prativrutta";
    public static final String NOTICE155 = "Notice 155";
    public static final String NOTICE156 = "Notice 156";
    public static final String NOTICE159 = "Notice 159";
    public static final String MUTATION_CERTIFICATE = "MutationCertificate";

    // Notice number sequence strings for generating sequence number
    public static final String OBJECTION_SEQ_STR = "PT_OBJECTION_NO";
    public static final String HEARINGNO_SEQ_STR = "PT_HEARINGNO";
    public static final String MUTATIONAPPLNO_SEQ_STR = "PT_MUT_APPLNO";
    public static final String MEMONO_SEQ_STR = "PT_MEMO_NO";
    public static final String REJECTION_SEQ_STR = "PT_REJECTION_NO";

    // Report names
    public static final String REPORT_TEMPLATENAME_NOTICE127 = "notice127";
    public static final String REPORT_TEMPLATENAME_NOTICE134 = "notice134";
    public static final String REPORT_TEMPLATENAME_NOTICE125 = "notice125";
    public static final String REPORT_TEMPLATENAME_NOTICE_PRATIVRUTTA = "Notice_Prativrutta";
    public static final String WARRANT_APPLICATION = "Warrant-application";
    public static final String REPORT_TEMPLATENAME_EGS_EDU_CESS_COLLECTION = "Egs_EduCess_CollectionInfo";
    public static final String REPORT_TEMPLATENAME_CALSHEET_FOR_GOVT_PROPS = "IndCalSheetForGovtProperties";
    public static final String REPORT_TEMPLATENAME_ARREARREGISTER = "ArrearRegister";
    public static final String REPORT_TEMPLATENAME_BAKAYAFERIST = "Bakaya_Ferist";
    public static final String REPORT_TEMPLATENAME_HEADWISEDMDCOLL = "HeadWiseDmdNdRec";
    public static final String REPORT_TEMPLATENAME_BIGBUILDINGRECOVERY = "BigBuildingRecoveryStmt";
    public static final String REPORT_TEMPLATENAME_DAILY_COLLECTION = "DailyCollectionReport";
    public static final String REPORT_TEMPLATENAME_DAILY_ABSTRACT_RECOVERY_REPORT = "DailyAbstractRecoveryReport";
    public static final String REPORT_TEMPLATENAME_DCBREPORT = "DcbReport";

    // Occupancy Types
    public static final String OCC_TENANT = "TENANT";
    public static final String OCC_OWNER = "OWNER";
    public static final String OCC_COMMERCIAL = "COMMERCIAL";

    // Usage Types
    public static final String USAGE_RESIDENTIAL = "RESD";

    // TreeMap for Non Vacant Land types
    public static final Map<String, String> NON_VAC_LAND_PROPERTY_TYPE_CATEGORY = new TreeMap<String, String>() {
        private static final long serialVersionUID = -8758751964576480520L;
        {
            put(UNITTYPE_RESD, "Residential");
            put(CATEGORY_NON_RESIDENTIAL, "Non Residential");
            put(PROPTYPE_MIXED, "Mixed");
        }
    };

    // TreeMap for Vacant Land types
    public static final Map<String, String> VAC_LAND_PROPERTY_TYPE_CATEGORY = new HashMap<String, String>() {
        private static final long serialVersionUID = 4641144830413085281L;
        {
            put(CATEGORY_VACANT_LAND, "Private Land");
            put(OWNERSHIP_TYPE_STATE_GOVT, "State Government Land");
            put(CATEGORY_CENTRAL_GOVT, "Central Government Land");
        }
    };

    public static final Map<String, String> PROPERTY_TYPE_CATEGORIES = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            putAll(VAC_LAND_PROPERTY_TYPE_CATEGORY);
            putAll(NON_VAC_LAND_PROPERTY_TYPE_CATEGORY);
        }
    };

    public static final Map<String, String> DEVIATION_PERCENTAGE = new TreeMap<String, String>() {
        private static final long serialVersionUID = 2705261617790275152L;
        {
            put("1-10%", "1-10%");
            put("11-25%", "11-25%");
            put("26-100%", "26-100%");
        }
    };
    // Un authorized penalty percentages on property tax
    public static final BigDecimal DEVIATION_TAXPERC_10 = BigDecimal.valueOf(0.1);// 10%
    public static final BigDecimal BPA_DEVIATION_TAXPERC_1_10 = BigDecimal.valueOf(0.25);// 25%
    public static final BigDecimal BPA_DEVIATION_TAXPERC_ABOVE_11 = BigDecimal.valueOf(0.5);// 50%
    public static final BigDecimal BPA_DEVIATION_TAXPERC_NOT_DEFINED = BigDecimal.valueOf(1);// 100%

    // Named Queries
    public static final String QUERY_DEPRECIATION_BY_YEAR = "DEPRECIATION_BY_YEAR";
    public static final String QUERY_BASERATE_BY_OCCUPANCY_ZONE = "BASERATE_BY_OCCUPANCY_ZONE";
    public static final String QUERY_BASERATE_BY_ZONE_USAGE_STRUCTURE_OCCUPANCY = "BASERATE_BY_ZONE_USAGE_STRUCTURE_OCCUPANCY";
    public static final String QUERY_PROPERTY_BY_UPICNO_AND_STATUS = "getPropertyByUpicNoAndStatus";
    public static final String QUERY_INSTALLMENTLISTBY_MODULE_AND_STARTYEAR = "INSTALLMENTLISTBY_MODULE_AND_STARTYEAR";
    public static final String QUERY_DEMANDREASONBY_CODE_AND_INSTALLMENTID = "DEMANDREASONBY_CODE_AND_INSTALLMENTID";
    public static final String QUERY_DEMANDREASONDETAILS_BY_DEMANDREASONID_DATE = "DEMANDREASONDETAILS_BY_DEMANDREASONID_DATE";
    public static final String QUERY_BASERENT_BY_BOUNDARY_FOR_OPENPLOT = "QUERY_BASERENT_BY_BOUNDARY_FOR_OPENPLOT";
    public static final String QUERY_LATEST_BILL_FOR_PROPERTY = "getLatestBillsForProperty";
    public static final String QUERY_BILLRECEIPT_FOR_BILL = "getBillRcptGForBill";
    public static final String QUERY_INSTALLMENTBY_MODULE_AND_DESC = "INSTALLMENTBY_MODULE_AND_DESC";
    public static final String QUERY_BILLDETAIL_BILL_INST_RSN = "getBillDetailsforBillANDInstAndRsn";
    public static final String QUERY_PROPSTATVALUE_BY_BASICPROPID_CODE_ISACTIVE = "getPropStatValByBasicPropIdAndStatCodeAndISActive";
    public static final String QUERY_NOCOLL_DEMANDDET_FOR_PROPERTY = "getNoCollDemandDetForProperty";
    public static final String QUERY_NOCOLL_DEMANDDET_FOR_PROPERTY_FORINST = "getNoCollDemandDetForPropertyAndInst";
    public static final String QUERY_DEMANDDET_FOR_PROPERTY = "getDemandDetForProperty";
    public static final String QUERY_DEMANDDETAILS_EXCLUDING_REASONS = "demandDetailsExcludingReasons";
    public static final String QUERY_PROPERTIES_FOR_PENALTY_CALCULATION = "propertiesForPenaltyCalculation";
    public static final String QUERY_DEMAND_BILL_STATUS = "demandBillstatus";
    public static final String QUERY_INSTALLMENTLISTBY_MODULE_AND_FINANCIALYYEAR = "INSTALLMENTLISTBY_MODULE_AND_FINANCIALYYEAR";
    public static final String QUERY_INSTALLMENTLISTBY_MODULE_AND_FINANCIALYYEAR_DESC = "INSTALLMENTLISTBY_MODULE_AND_FINANCIALYYEAR_DESC";

    // Base Rents
    public static final BigDecimal BASERENT_FROM_APRIL2008_BUILDINGS = new BigDecimal("10");
    public static final BigDecimal BASERENT_FROM_APRIL2008_OPENPLOT = new BigDecimal("5");

    // Standard Deduction 10%
    public static final Double STANDARD_DEDUCTION_PERCENTAGE = Double.valueOf(.1d);

    // ALV Constant for Large Residential Premises Tax
    public static final BigDecimal LARGE_RESIDENTIAL_PREMISES_ALV = new BigDecimal("1500");

    // Others
    public static final String STRING_SEPERATOR = "-";
    /*
     * it is break date for rent chart and base rate methods, as now we want only rent chart calculation on system for all the
     * installments, we changed this date to 2020. its just hack
     */
    public static final String DATE_CONSTANT = "01/04/2020";

    public static final char BLANK_CHAR = ' ';
    public static final char PIPE_CHAR = '|';
    public static final String COMMA_STR = ",";
    public static final String BLANK_STR = " ";
    public static final String EMPTY_STR = "";

    // Demand Reason master codes
    public static final String DEMANDRSN_CODE_GENERAL_TAX = "GEN_TAX";
    public static final String DEMANDRSN_CODE_VACANT_TAX = "VAC_LAND_TAX";
    public static final String DEMANDRSN_CODE_LIBRARY_CESS = "LIB_CESS";
    public static final String DEMANDRSN_CODE_EDUCATIONAL_TAX = "EDU_TAX";
    public static final String DEMANDRSN_CODE_SEWERAGE_TAX = "SEW_TAX";
    public static final String DEMANDRSN_CODE_PRIMARY_SERVICE_CHARGES = "PRIMARY_SER_CHRG";
    public static final String DEMANDRSN_CODE_UNAUTHORIZED_PENALTY = "UNAUTH_PENALTY";
    public static final String DEMANDRSN_CODE_CHQ_BOUNCE_PENALTY = "CHQ_BUNC_PENALTY";
    public static final String DEMANDRSN_CODE_PENALTY_FINES = "PENALTY_FINES";
    public static final String DEMANDRSN_CODE_ADVANCE = "ADVANCE";
    public static final String DEMANDRSN_CODE_REBATE = "REBATE";
    public static final String DEMANDRSN_CODE_WARRANT_FEE = "WARRANT_FEE";
    public static final String DEMANDRSN_CODE_NOTICE_FEE = "NOTICE_FEE";
    public static final String DEMANDRSN_CODE_COURT_FEE = "COURT_FEE";
    public static final String DEMANDRSN_CODE_RECOVERY_FEE = "RECOVERY_FEE";
    public static final String DEMANDRSN_CODE_DRAINAGE_TAX = "DRAINAGE_TAX";
    public static final String DEMANDRSN_CODE_SCAVENGE_TAX = "SCAVENGE_TAX";
    public static final String DEMANDRSN_CODE_LIGHT_TAX = "LIGHT_TAX";
    public static final String DEMANDRSN_CODE_WATER_TAX = "WATER_TAX";

    // Demand Reason master Strings
    public static final String DEMANDRSN_STR_GENERAL_TAX = "General Tax";
    public static final String DEMANDRSN_STR_VACANT_TAX = "Vacant Land Tax";
    public static final String DEMANDRSN_STR_LIBRARY_CESS = "Library Cess";
    public static final String DEMANDRSN_STR_EDUCATIONAL_TAX = "Education Tax";
    public static final String DEMANDRSN_STR_UNAUTHORIZED_PENALTY = "Unauthorized Penalty";
    public static final String DEMANDRSN_STR_CHQ_BOUNCE_PENALTY = "Cheque Bounce Penalty";
    public static final String DEMANDRSN_STR_PENALTY_FINES = "Penalty Fines";
    public static final String DEMANDRSN_STR_ADVANCE = "Advance";
    public static final String DEMANDRSN_STR_ADVANCE_REBATE = "ADVANCE_REBATE";
    public static final String DEMANDRSN_STR_DRAINAGE_TAX = "Drainage Tax";
    public static final String DEMANDRSN_STR_SCAVENGE_TAX = "Scavenging Tax";
    public static final String DEMANDRSN_STR_LIGHT_TAX = "Lighting Tax";
    public static final String DEMANDRSN_STR_WATER_TAX = "Water Tax";

    // Tax Payable constants
    public static final String DEMANDRSN_CODE_GEN_TAX_PAYABLE = "PROPERTY TAX-PROPERTY TAX ON ALL PROPERTIES";
    public static final String DEMANDRSN_CODE_EDUCATIONAL_CESS_PAYABLE = "EDUCATION CESS PAYABLE";
    public static final String DEMANDRSN_CODE_BIG_RESIDENTIAL_BLDG_TAX_PAYABLE = "BIG RESIDENTIAL BUILDING TAX PAYABLE";
    public static final String DEMANDRSN_CODE_EMPLOYEE_GUARANTEE_TAX_PAYABLE = "EMPLOYMENT GUARANTEE CESS PAYABLE";
    public static final String DEMANDRSN_CODE_GENERAL_WATER_TAX_PAYABLE = "WATER TAX-WATER TAX";
    public static final String DEMANDRSN_CODE_SEWERAGE_TAX_PAYABLE = "SEWERAGE TAX-SEWERAGE TAX";
    public static final String DEMANDRSN_CODE_FIRE_SERVICE_TAX_PAYABLE = "FIRE SERVICE TAX PAYABLE";
    public static final String DEMANDRSN_CODE_LIGHTINGTAX_PAYABLE = "LIGHTING TAX-LIGHT TAX";

    // HashMap for order of demand reasons to be set in bill
    public static final Map<String, Integer> DEMAND_REASON_ORDER_MAP = new HashMap<String, Integer>() {
        private static final long serialVersionUID = -376251525790947906L;

        {
            put(DEMANDRSN_CODE_CHQ_BOUNCE_PENALTY, 0);
            put(DEMANDRSN_CODE_PENALTY_FINES, 1);
            put(DEMANDRSN_CODE_GENERAL_TAX, 2);
            put(DEMANDRSN_CODE_SCAVENGE_TAX, 3);
            put(DEMANDRSN_CODE_DRAINAGE_TAX, 4);
            put(DEMANDRSN_CODE_LIGHT_TAX, 5);
            put(DEMANDRSN_CODE_WATER_TAX, 6);
            put(DEMANDRSN_CODE_VACANT_TAX, 7);
            put(DEMANDRSN_CODE_UNAUTHORIZED_PENALTY, 8);
            put(DEMANDRSN_CODE_LIBRARY_CESS, 9);
            put(DEMANDRSN_CODE_EDUCATIONAL_TAX, 10);
            put(DEMANDRSN_CODE_SEWERAGE_TAX, 11);
            put(DEMANDRSN_CODE_REBATE, 12);
            put(DEMANDRSN_CODE_ADVANCE, 13);
        }
    };

    // General water rates
    public static final String WATER_METER = "WATER_METER";
    public static final String GWR_IMPOSED = "GWR_IMPOSED";
    public static final String GWR_NOT_IMPOSED = "GWR_NOT_IMPOSED";
    public static final String WATER_LINE_WITHOUT_METER = "WATER_LINE_WITHOUT_METER";
    public static final String WATER_LINE_WITH_METER20 = "WATER_LINE_WITH_METER20";

    public static final String EFFECTIVE_ASSESSMENT_PERIOD1 = "1 Jan";
    public static final String EFFECTIVE_ASSESSMENT_PERIOD2 = "1 Apr";
    public static final String EFFECTIVE_ASSESSMENT_PERIOD3 = "1 Jul";
    public static final String EFFECTIVE_ASSESSMENT_PERIOD4 = "1 Oct";

    public static final TreeMap<Integer, String> FLOOR_MAP = new TreeMap<Integer, String>() {
        private static final long serialVersionUID = 464912402295082366L;

        {
            put(-9, "Cellar");
            put(-8, "Mezzanine");
            put(-3, "Basement-2");
            put(-2, "Basement-1");
            put(-1, "Stilt Floor");
            put(0, "Ground Floor");
            put(1, "1st floor");
            put(2, "2nd Floor");
            put(3, "3rd Floor");
            put(4, "4th Floor");
            put(5, "5th Floor");
            put(6, "6th Floor");
            put(7, "7th Floor");
            put(8, "8th Floor");
            put(9, "9th Floor");
            put(10, "10th Floor");
            put(11, "11th Floor");
            put(12, "12th Floor");
            put(13, "13th Floor");
            put(14, "14th Floor");
            put(15, "15th Floor");
            put(16, "16th Floor");
            put(17, "17th Floor");
            put(18, "18th Floor");
            put(19, "19th Floor");
            put(20, "20th Floor");
            put(21, "21st Floor");
            put(22, "22nd Floor");
            put(23, "23rd Floor");
            put(24, "24th Floor");
            put(25, "25th Floor");
            put(26, "26th Floor");
            put(27, "27th Floor");
            put(28, "28th Floor");
            put(29, "29th Floor");
            put(30, "30th Floor");
            put(31, "31st floor");
            put(32, "32nd Floor");
            put(33, "33rd Floor");
            put(34, "34th Floor");
            put(35, "35th Floor");
            put(36, "36th Floor");
            put(37, "37th Floor");
            put(38, "38th Floor");
            put(39, "39th Floor");
            put(40, "40th Floor");
            put(41, "41st floor");
            put(42, "42nd Floor");
            put(43, "43rd Floor");
            put(44, "44th Floor");
            put(45, "45th Floor");
            put(46, "46th Floor");
            put(47, "47th Floor");
            put(48, "48th Floor");
            put(49, "49th Floor");
            put(50, "50th Floor");
            put(99, "Pent House");
        }
    };

    // workflow step names
    public static final String WFLOW_ACTION_STEP_SAVE = "Save";
    public static final String WFLOW_ACTION_STEP_FORWARD = "Forward";
    public static final String WFLOW_ACTION_STEP_APPROVE = "Approve";
    public static final String WFLOW_ACTION_STEP_SIGN = "Sign";
    public static final String WFLOW_ACTION_STEP_PREVIEW = "Preview";
    public static final String WFLOW_ACTION_STEP_REJECT = "Reject";
    public static final String WFLOW_ACTION_STEP_CANCEL = "Cancel";
    public static final String WFLOW_ACTION_STEP_NOTICE_GENERATE = "Generate Notice";
    public static final String WFLOW_ACTION_STEP_PRINT_NOTICE = "Print Special Notice";
    public static final String WFLOW_ACTION_STEP_GENERATE_TRANSFER_NOTICE = "Generate Title Transfer Notice";

    // workflow action names
    public static final String WFLOW_ACTION_NAME_CREATE = "Create";
    public static final String WFLOW_ACTION_NAME_TRANSFER = "Mutation";
    public static final String WFLOW_ACTION_NAME_DEACTIVATE = "Deactivate";
    public static final String WFLOW_ACTION_NAME_CHANGEADDRESS = "ChangeAddress";
    public static final String WFLOW_ACTION_NAME_MODIFY = "Modify";
    public static final String WFLOW_ACTION_NAME_ALTER = "Alter";
    public static final String WFLOW_ACTION_NAME_NEW = "New";
    public static final String WFLOW_ACTION_NAME_BIFURCATE = "Bifurcate";
    public static final String WFLOW_ACTION_NAME_GENERATE_NOTICE = "NoticeGeneration";
    public static final String WFLOW_ACTION_END = "END";
    public static final String WFLOW_ACTION_READY_FOR_PAYMENT = "Ready For Payment";
    public static final String WFLOW_ACTION_NEW = "NEW";
    public static final String WFLOW_ACTION_NAME_DEMOLITION = "Demolition";
    public static final String WFLOW_ACTION_NAME_EXEMPTION = "Exemption";
    public static final String WFLOW_ACTION_NAME_GRP = "GRP";
    public static final String APPLICATION_TYPE_WRITE_OFF = "WRITEOFF";

    // WORKFLOW property states
    public static final String WF_STATE_NEW = "New";
    public static final String WF_STATE_APPROVAL_PENDING = "Approval_Pending";
    public static final String WF_STATE_NOTICE_GENERATION_PENDING = "Notice_Generation_Pending";
    public static final String WF_STATE_NOTICE_GENERATED = "Notice Generated";
    public static final String WF_STATE_COMMISSIONER_APPROVED = "Commissioner Approved";
    public static final String WF_STATE_DIGITALLY_SIGNED = "Digitally Signed";
    public static final String WF_STATE_DIGITAL_SIGNATURE_PENDING = "Digital Signature Pending";
    public static final String WF_STATE_REVENUE_OFFICER_APPROVED = "Revenue officer Approved";
    public static final String WF_STATE_REJECTED = "Rejected";
    public static final String WF_STATE_COMMISSIONER_APPROVAL_PENDING = "Commissioner Approval Pending";
    public static final String WF_STATE_CLOSED = "Closed";
    public static final String WF_STATE_ASSISTANT_APPROVAL_PENDING = "Assistant Approval Pending";
    public static final String WF_STATE_BILL_COLLECTOR_APPROVED = "Bill Collector Approved";
    public static final String WF_STATE_ASSISTANT_APPROVED = "Assistant Approved";
    public static final String WF_STATE_REVENUE_OFFICER_APPROVAL_PENDING = "Revenue Officer Approval Pending";
    public static final String WF_STATE_REGISTRATION_PENDING = "Registration Pending";
    public static final String WF_STATE_REGISTRATION_COMPLETED = "Registration Completed";
    public static final String WF_STATE_BILL_COLLECTOR_APPROVAL_PENDING = "Bill Collector Approval Pending";
    public static final String WF_STATE_NOTICE_PRINT_PENDING = "Notice Print Pending";
    public static final String WF_STATE_ASSISTANT_FORWARD_PENDING = "Assistant Forward Pending";

    public static final String REPORT_TEMPLATENAME_BILL_GENERATION = "propertybill";

    // Prefix for sequence names, for bill number generation
    public static final String BILLGEN_SEQNAME_PREFIX = "BDIV-";
    public static final String MANUAL_BILLGEN_SEQNAME_PREFIX = "MBDIV-";

    // Type of demand dues
    public static final String ARREARS_DMD = "ARREARS";
    public static final String CURRENT_DMD = "CURRENT";

    public static final String ARREARS_DEMAND = "ARREARS_DEMAND";
    public static final String CURRENT_DEMAND = "CURRENT_DEMAND";

    // Voucher constants
    public static final String VOUCH_CREATE_RSN_CREATE = "Create";
    public static final String VOUCH_CREATE_RSN_MODIFY = "Modify";
    public static final String VOUCH_CREATE_RSN_DEACTIVATE = "Deactivate";

    // Property Creation reasons
    public static final String PROP_CREATE_RSN = "CREATE";
    public static final String PROP_CREATE_RSN_BIFUR = "BIFUR";

    // Property is default values
    public static final Character PROPERTY_IS_DEFAULT = 'Y';
    public static final Character PROPERTY_IS_NOT_DEFAULT = 'N';
    public static final String PROP_CREATE_RSN_NEWPROPERTY_CODE = "NEW";

    // Property Modification Reasons
    public static final String PROPERTY_MODIFY_REASON_AMALG = "AMALG";
    public static final String PROPERTY_MODIFY_REASON_BIFURCATE = "BIFURCATE";
    public static final String PROPERTY_MODIFY_REASON_OBJ = "OBJ";
    public static final String PROPERTY_MODIFY_REASON_MODIFY = "MODIFY";
    public static final String PROPERTY_MODIFY_REASON_COURT_RULE = "COURT_RULE";
    public static final String PROPERTY_MODIFY_REASON_DATA_ENTRY = "DATA_ENTRY";
    public static final String PROPERTY_MODIFY_REASON_DATA_UPDATE = "DATA_UPDATE";
    public static final String PROPERTY_MODIFY_REASON_EDIT_OWNER = "EDIT_OWNER";
    public static final String PROPERTY_MODIFY_REASON_ADD_OR_ALTER = "ADD_OR_ALTER";
    public static final String PROPERTY_MODIFY_REASON_EDIT_DATA_ENTRY = "EDIT_DATA_ENTRY";
    public static final String PROPERTY_MODIFY_REASON_FULL_DEMOLITION = "FULL DEMOLITION";
    public static final String PROPERTY_MODIFY_REASON_TAX_EXEMPTION = "TAX EXEMPTION";
    public static final String PROPERTY_MODIFY_REASON_GENERAL_REVISION_PETITION = "GRP";
    public static final String PROPERTY_MODIFY_REASON_BULK_BOUNDARY = "BULK BOUNDARY UPDATION";

    // Constants for Government Properties
    public static final Double STATEGOVT_BUILDING_ALV_PERCENTAGE = Double.valueOf(8.25d);
    public static final Double CENTRALGOVT_BUILDING_ALV_PERCENTAGE = Double.valueOf(9d);
    public static final Double STATEGOVT_BUILDING_GENERALTAX_ADDITIONALDEDUCTION = Double.valueOf(20d);

    // Amenities
    public static final String AMENITY_TYPE_FULL = "Full";
    public static final String AMENITY_TYPE_PARTIAL = "Partial";
    public static final String AMENITY_TYPE_NIL = "Nil";
    public static final Double AMENITY_PERCENTAGE_FULL = Double.valueOf(75d);
    public static final Double AMENITY_PERCENTAGE_PARTIAL = Double.valueOf(50d);
    public static final Double AMENITY_PERCENTAGE_NIL = Double.valueOf(33.33d);

    // Residential Cum Commercial properties
    public static final Double RESD_CUM_COMMERCIAL_PROP_ALV_PERCENTAGE = Double.valueOf(50d);

    // Property Status
    public static final String PROPERTY_STATUS_INACTIVE = "INACTIVE";
    public static final String PROP_STATUS_TYPE_DEACT = "DEACTIVATE";

    public static final String DEFAULT_FUNCTIONARY_CODE = "FUNCTIONARY_CODE";
    public static final String DEFAULT_FUND_SRC_CODE = "FUND_SOURCE_CODE";
    public static final String DEFAULT_FUND_CODE = "FUND_CODE";
    public static final String DEPT_CODE_TAX = "DEPARTMENT_CODE";
    public static final String FUNCTION_CODE = "FUNCTION_CODE";

    // Roles
    public static final String CSC_OPERATOR_ROLE = "CSC Operator";
    public static final String ASSISTANT_ROLE = "ASSISTANT";
    public static final String PTVERIFIER_ROLE = "Property Verifier";
    public static final String PTAPPROVER_ROLE = "Property Approver";
    public static final String PTCREATOR_ROLE = "PTCreator";
    public static final String PTVALIDATOR_ROLE = "PTValidator";
    public static final String ROLE_COLLECTION_OPERATOR = "Collection Operator";
    public static final String ROLE_PTADMINISTRATOR = "Property Administrator";
    public static final String ROLE_DATAENTRY_OPERATOR = "Data Entry Operator";

    // Designations
    public static final String ASSISTANT_DESGN = "Assistant";
    public static final String REVENUE_OFFICER_DESGN = "Revenue Officer";
    public static final String REVENUE_INSPECTOR_DESGN = "UD Revenue Inspector";
    public static final String REVENUE_CLERK_DESGN = "Revenue Clerk";
    public static final String COMMISSIONER_DESGN = "Commissioner";
    public static final String BILL_COLLECTOR_DESGN = "Bill Collector";
    public static final String TAX_COLLECTOR_DESGN = "Tax Collector";
    public static final String END_APPROVER_DESGN = "RO";
    public static final String JUNIOR_ASSISTANT = "Junior Assistant";
    public static final String SENIOR_ASSISTANT = "Senior Assistant";
    public static final String ASSISTANT_COMMISSIONER_DESIGN = "Assistant Commissioner";
    public static final String DEPUTY_COMMISSIONER_DESIGN = "Deputy Commissioner";
    public static final String ADDITIONAL_COMMISSIONER_DESIGN = "Additional Commissioner";
    public static final String ZONAL_COMMISSIONER_DESIGN = "Zonal Commissioner";
    public static final String JUNIOR_OR_SENIOR_ASSISTANT_DESIGN = "Senior Assistant,Junior Assistant";

    public static final String NEW_ASSESSMENT = "NEW ASSESSMENT";
    public static final String ADDTIONAL_RULE_ALTER_ASSESSMENT = "ALTER ASSESSMENT";
    public static final String ADDTIONAL_RULE_BIFURCATE_ASSESSMENT = "BIFURCATE ASSESSMENT";
    public static final String ADDTIONAL_RULE_PROPERTY_TRANSFER = "PROPERTY TRANSFER";
    public static final String ADDTIONAL_RULE_PARTIAL_TRANSFER = "PARTIAL TRANSFER";
    public static final String ADDITIONAL_RULE_FULL_TRANSFER = "FULL TRANSFER";
    public static final String ADDTIONAL_RULE_REGISTERED_TRANSFER = "REGISTERED TRANSFER";
    public static final String WFSTATUS = "WFSTATUS";
    public static final String WFOWNER = "WFOWNER";
    public static final String DEMOLITION = "DEMOLITION";
    public static final String EXEMPTION = "EXEMPTION";
    public static final String GENERAL_REVISION_PETITION = "GENERAL REVISION PETITION";
    public static final String AMALGAMATION = "AMALGAMATION";

    // GIS
    public static final String GISCITY = "nmc";
    public static final String GISVERSION = "/mapguide/";

    public static final String ARREAR_DEMANDRSN_GLCODE = "4311004";
    public static final String CURRENT_DEMANDRSN_GLCODE = "4311001";
    public static final String PRIOR_INCOME ="PRIOR_INCOME";
    // HashMap for GLCodes for Current Taxes
    public static final ImmutableMap<String, String> GLCODEMAP_FOR_CURRENTTAX = ImmutableMap.<String, String> builder()

            .put(DEMANDRSN_CODE_GENERAL_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_WATER_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_SCAVENGE_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_DRAINAGE_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_LIGHT_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_LIBRARY_CESS, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_EDUCATIONAL_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_UNAUTHORIZED_PENALTY, CURRENT_DEMANDRSN_GLCODE).build();

    // HashMap for GLCodes for Arrear Taxes
    public static final ImmutableMap<String, String> GLCODEMAP_FOR_ARREARTAX = ImmutableMap.<String, String> builder()

            .put(DEMANDRSN_CODE_GENERAL_TAX, ARREAR_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_WATER_TAX, ARREAR_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_SCAVENGE_TAX, ARREAR_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_DRAINAGE_TAX, ARREAR_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_LIGHT_TAX, ARREAR_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_LIBRARY_CESS, ARREAR_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_EDUCATIONAL_TAX, ARREAR_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_UNAUTHORIZED_PENALTY, ARREAR_DEMANDRSN_GLCODE).build();

    // HashMap for GLCodes for Tax Payables
    public static final ImmutableMap<String, String> GLCODEMAP_FOR_TAX_PAYABLE = ImmutableMap.<String, String> builder()

            .put(DEMANDRSN_CODE_GENERAL_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_WATER_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_SCAVENGE_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_DRAINAGE_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_LIGHT_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_LIBRARY_CESS, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_EDUCATIONAL_TAX, CURRENT_DEMANDRSN_GLCODE)
            .put(DEMANDRSN_CODE_UNAUTHORIZED_PENALTY, CURRENT_DEMANDRSN_GLCODE).build();

    // List for GLCodes for Current Taxes
    public static final List<String> GLCODES_FOR_CURRENTTAX = new ArrayList<String>() {
        private static final long serialVersionUID = -6532281844201057959L;

        {
            for (final Map.Entry<String, String> glCode : GLCODEMAP_FOR_CURRENTTAX.entrySet())
                add(glCode.getValue());
        }
    };

    // List for GLCodes for Arrear Taxes
    public static final List<String> GLCODES_FOR_ARREARTAX = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            for (final Map.Entry<String, String> glCode : GLCODEMAP_FOR_ARREARTAX.entrySet())
                add(glCode.getValue());
        }
    };

    // HashMap map b/n Demand reason string and code
    public static final Map<String, String> BUILTUP_PROPERTY_DMDRSN_CODE_MAP = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(DEMANDRSN_STR_GENERAL_TAX, DEMANDRSN_CODE_GENERAL_TAX);
            put(DEMANDRSN_STR_SCAVENGE_TAX, DEMANDRSN_CODE_SCAVENGE_TAX);
            put(DEMANDRSN_STR_DRAINAGE_TAX, DEMANDRSN_CODE_DRAINAGE_TAX);
            put(DEMANDRSN_STR_LIGHT_TAX, DEMANDRSN_CODE_LIGHT_TAX);
            put(DEMANDRSN_STR_WATER_TAX, DEMANDRSN_CODE_WATER_TAX);
            put(DEMANDRSN_STR_LIBRARY_CESS, DEMANDRSN_CODE_LIBRARY_CESS);
            put(DEMANDRSN_STR_EDUCATIONAL_TAX, DEMANDRSN_CODE_EDUCATIONAL_TAX);
            put(DEMANDRSN_STR_UNAUTHORIZED_PENALTY, DEMANDRSN_CODE_UNAUTHORIZED_PENALTY);
        }
    };

    public static final Map<String, String> VACANT_PROPERTY_DMDRSN_CODE_MAP = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(DEMANDRSN_STR_VACANT_TAX, DEMANDRSN_CODE_VACANT_TAX);
            put(DEMANDRSN_STR_LIBRARY_CESS, DEMANDRSN_CODE_LIBRARY_CESS);
            put(DEMANDRSN_STR_EDUCATIONAL_TAX, DEMANDRSN_CODE_EDUCATIONAL_TAX);
        }
    };

    public static final String GLCODE_FOR_TAXREBATE = "1109004";// 2801002,
                                                                // 2202103
    public static final String GLCODE_FOR_PENALTY = "1402010";// 1402001,1402002
    // 4314208

    public static final String GLCODE_FOR_ADVANCE_REBATE = "4314209";
    public static final String GLCODE_FOR_ADVANCE = "3504101";// 3504102

    public static final BigDecimal FIRST_REBATETAX_PERC = new BigDecimal("4");
    public static final BigDecimal SECOND_REBATETAX_PERC = new BigDecimal("2");
    public static final BigDecimal CHQ_BOUNCE_PENALTY = new BigDecimal("750");

    // Penalty
    public static final String ARR_LP_DATE_CONSTANT = "01/06/2010";
    public static final BigDecimal LP_PERCENTAGE_CONSTANT = new BigDecimal(2);
    public static final String ARR_LP_DATE_BREAKUP = "01/04/2010";
    public static final String PENALTY_WATERTAX_EFFECTIVE_DATE = "01/01/2012";

    public static final String APPCONFIG_TAXCALC_RULE_RENTCHART = "PTTAXCALC_RULE_RENTCHART";

    // Property Docs
    public static final String DOCS_CREATE_PROPERTY = "CREATE";
    public static final String DOCS_MUTATION_PROPERTY = "MUTATION";
    public static final String DOCS_MODIFY_PROPERTY = "MODIFICATION";
    public static final String DOCS_AMALGAMATE_PROPERTY = "AMALGAMATION";
    public static final String DOCS_BIFURCATE_PROPERTY = "BIFURCATION";
    public static final String DOCS_ADDRESS_CHANGE_PROPERTY = "PROPERTY ADDRESS CHANGE";
    public static final String DOCS_DEACTIVATE_PROPERTY = "DEACTIVATE";

    public static final Map<String, String> FLOOR_TYPES = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("Varandah", "Varandah");
            put("Loft", "Loft");
            put("Mezzanine Floor", "Mezzanine Floor");
            put("Covered Parking", "Covered Parking");
            put("Uncovered Parking", "Uncovered Parking");
            put("Basement", "Basement");
            put("Godown", "Godown");
        }
    };

    // Unit Types
    public static final String UNITTYPE_OPEN_PLOT = "OPEN_PLOT";
    public static final String UNITTYPE_RESD = "RESIDENTIAL";
    public static final String UNITTYPE_NON_RESD = "NON_RESD";

    public static final Integer OPEN_PLOT_UNIT_FLOORNUMBER = -3;

    public static final Map<Integer, String> MONTHS_MAP = new TreeMap<Integer, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(JANUARY, "Jan");
            put(FEBRUARY, "Feb");
            put(MARCH, "Mar");
            put(APRIL, "Apr");
            put(MAY, "May");
            put(JUNE, "Jun");
            put(JULY, "Jul");
            put(AUGUST, "Aug");
            put(SEPTEMBER, "Sep");
            put(OCTOBER, "Oct");
            put(NOVEMBER, "Nov");
            put(DECEMBER, "Dec");
        }
    };

    public static final String GLCODE_FOR_EDU_CESS_ARREARS = "4312001";
    public static final String GLCODE_FOR_EDU_CESS_CURRENT = "4312002";
    public static final String GLCODE_FOR_EGS_CESS_ARREARS = "4312003";
    public static final String GLCODE_FOR_EGS_CESS_CURRENT = "4312004";
    public static final String GLCODE_FOR_GENERAL_TAX_CURRENT = "4311002";
    public static final String GLCODE_FOR_FIRE_SERVICE_TAX_ARREARS = "4311011";
    public static final String GLCODE_FOR_FIRE_SERVICE_TAX_CURRENT = "4311012";
    public static final String GLCODE_FOR_GENERAL_WATER_TAX_ARREARS = "4311021";
    public static final String GLCODE_FOR_GENERAL_WATER_TAX_CURRENT = "4311022";
    public static final String GLCODE_FOR_SEWERAGE_TAX_ARREARS = "4311031";
    public static final String GLCODE_FOR_SEWERAGE_TAX_CURRENT = "4311032";
    public static final String GLCODE_FOR_LIGHTINGTAX_ARREARS = "4311041";
    public static final String GLCODE_FOR_LIGHTINGTAX_CURRENT = "4311042";
    public static final String GLCODE_FOR_BIG_RESIDENTIAL_BLDG_TAX_ARREARS = "4311003";
    public static final String GLCODE_FOR_BIG_RESIDENTIAL_BLDG_TAX_CURRENT = ARREAR_DEMANDRSN_GLCODE;
    public static final String GLCODE_FOR_MUTATION_FEE = "1404009";

    public static final List<String> EDU_EGS_CESS_GLCODE_LIST = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(GLCODE_FOR_EDU_CESS_ARREARS);
            add(GLCODE_FOR_EDU_CESS_CURRENT);
            add(GLCODE_FOR_EGS_CESS_ARREARS);
            add(GLCODE_FOR_EGS_CESS_CURRENT);
        }
    };

    public static final String STR_EGS_CESS = "EGS Cess";

    public static final String REPORT_START_DATE = "01/04/2012";

    public static final List<String> FLOORNO_WITH_DIFF_MULFACTOR_RESD = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("-5");
            add("-4");
            add("0");
            add("1");
            add("2");
        }
    };

    public static final List<String> FLOORNO_WITH_DIFF_MULFACTOR_NONRESD = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("-5");
            add("-4");
            add("0");
        }
    };

    public static final String OPEN_PLOT_SHORTFORM = "OP";
    public static final String RESD_SHORTFORM = "R";
    public static final String NONRESD_SHORTFORM = "NR";
    public static final String STATE_GOVT_SHORTFORM = "SGovt";
    public static final String CENTRAL_GOVT_SHORTFORM = "CGovt";
    public static final String MIXED_SHORTFORM = "R-NR";

    // FLoor Types
    public static final String GODOWN = "Godown";
    public static final String BASEMENT = "Basement";

    // 1Sq.Mt = 10.76Sq.Ft
    public static final BigDecimal SqFt = BigDecimal.valueOf(10.76);

    // Used during Notice Generation
    public static final String STYLE_TAG_BEGIN = "<style forecolor=\"#000000\" isBold=\"true\" pdfFontName=\"Times-Bold\" pdfEncoding=\"Cp1252\" isPdfEmbedded=\"true\">";
    public static final String STYLE_TAG_END = "</style>";

    public static final String ROLE_OPERATOR = "Operator";

    // antisamy hack
    public static final String AMP_ENCODED_STR = "&amp;";
    public static final String AMP_ACTUAL_STR = "&";

    public static final List<String> DEMAND_RSNS_LIST = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(DEMANDRSN_CODE_GENERAL_TAX);
            add(DEMANDRSN_CODE_VACANT_TAX);
            add(DEMANDRSN_CODE_SCAVENGE_TAX);
            add(DEMANDRSN_CODE_DRAINAGE_TAX);
            add(DEMANDRSN_CODE_LIGHT_TAX);
            add(DEMANDRSN_CODE_WATER_TAX);
            add(DEMANDRSN_CODE_UNAUTHORIZED_PENALTY);
            add(DEMANDRSN_CODE_LIBRARY_CESS);
            add(DEMANDRSN_CODE_EDUCATIONAL_TAX);
        }
    };

    public static final Map<String, String> waterRates = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(WATER_METER, "Water Meter");
            put(GWR_IMPOSED, "GWR to be imposed");
            put(GWR_NOT_IMPOSED, "GWR not to be imposed");
            put(WATER_LINE_WITHOUT_METER, "Water Line Without Meter");
            put(WATER_LINE_WITH_METER20, "Water line without meter 20 mm dia");
        }
    };

    public static final String NOTICE_TYPE_BILL = "Bill";
    public static final String NOTICE_TYPE_SPECIAL_NOTICE = "Special Notice";
    public static final String NOTICE_TYPE_MUTATION_CERTIFICATE = "Mutation Certificate";
    public static final String NOTICE_TYPE_DEMAND_BILL = "Demand Bill";
    public static final String NOTICE_TYPE_RPPROCEEDINGS = "RP Proceedings";
    public static final String NOTICE_TYPE_RPHEARINGS = "RP Hearing Notice";

    public static final String CREATE_AUDIT_ACTION = "Create Property";
    public static final String MODIFY_AUDIT_ACTION = "Modify Property";
    public static final String AMALG_AUDIT_ACTION = "Amalgamate Property";
    public static final String BIFUR_AUDIT_ACTION = "Bifurcate Property";
    public static final String TRANSFER_AUDIT_ACTION = "Transfer Property";
    public static final String CHANGEADDRESS_AUDIT_ACTION = "Change Property Address";
    public static final String DEACTIVE_AUDIT_ACTION = "Deactivate Property";
    public static final String AUDITDATA_STRING_SEP = "\n";
    public static final String DATAUPDATE_AUDIT_ACTION = "Assessment Data Update";
    public static final String EDIT_DEMAND_AUDIT_ACTION = "Edit Demand";
    public static final String EDIT_OWNER_AUDIT_ACTION = "Edit Owner";

    public static final String APPCONFIG_KEY_BULKBILL_WARD = "WardNum";
    public static final Integer QUARTZ_BULKBILL_JOBS = 10;

    public static final List<String> ORDERED_DEMAND_RSNS_LIST = ImmutableList.of(DEMANDRSN_CODE_PENALTY_FINES,
            DEMANDRSN_CODE_GENERAL_TAX, DEMANDRSN_CODE_VACANT_TAX, DEMANDRSN_CODE_SCAVENGE_TAX,
            DEMANDRSN_CODE_DRAINAGE_TAX, DEMANDRSN_CODE_LIGHT_TAX, DEMANDRSN_CODE_WATER_TAX,
            DEMANDRSN_CODE_UNAUTHORIZED_PENALTY, DEMANDRSN_CODE_LIBRARY_CESS, DEMANDRSN_CODE_EDUCATIONAL_TAX,
            DEMANDRSN_CODE_SEWERAGE_TAX, DEMANDRSN_CODE_REBATE, DEMANDRSN_CODE_ADVANCE,
            DEMANDRSN_CODE_CHQ_BOUNCE_PENALTY);

    public static final String APPCONFIG_KEY_WARDSFOR_TAXXMLMIGRTN = "WardNo";

    public static final Character HISTORY_TAX_DETAIL = 'Y';
    public static final Character NON_HISTORY_TAX_DETAIL = 'N';
    public static final String FORMAT_YEAR = "yyyy";

    public static final Character STATUS_MIGRATED = 'Y';
    public static final Character STATUS_NON_MIGRATED = 'N';

    public static final String MUTATION_CODE_NEW = "NEW";

    public static final String NOTICE_GENERATED_YES = "Yes";

    public static final Character STATUS_YES_XML_MIGRATION = 'Y';
    public static final Character STATUS_NO_XML_MIGRATION = 'N';
    public static final Character STATUS_FAIL_XML_MIGRATION = 'F';

    public static final String STR_MIGRATED = "Migrated";
    public static final String STR_MIGRATED_REMARKS = "Migrated from NMC Legacy DB";

    // Sequence for unit_identifier
    public static final String UNIT_IDENTIFIER_SEQ_STR = "PT_UNIT_IDENTIFIER";

    public static final String NOT_AVAILABLE = "N/A";

    public static final String PATTERN_BEGINS_WITH_1TO9 = "^[1-9][0-9]*";

    public static final Character STATUS_BILL_CREATED = 'Y';
    public static final Character STATUS_BILL_NOTCREATED = 'N';
    public static final Character STATUS_BILL_CREATE_FAILED = 'F';

    public static final String STRING_EMPTY = new String();

    public static final BigDecimal ADVANCE_REBATE_PERCENTAGE = new BigDecimal(5);
    public static final Integer MAX_ADVANCES_ALLOWED = 10;

    public static final String MUTATIONRS_OBJECTION_CODE = "OBJ";
    public static final String MUTATIONRS_OTHERS = "OTHERS";
    public static final String MUTATIONRS_COURT_ORDER = "Court Deed";
    public static final String MUTATIONRS_DECREE_BY_CIVIL_COURT = "Decree by Civil Court";
    public static final String MUTATIONRS_SUCCESSION = "Succession";
    public static final String MUTATIONRS_UNREG_WILL = "Unregistered Will Deed";
    public static final String MUTATIONRS_REG_WILL = "Registered Will Deed";
    public static final String CIVILCOURTDECREE = "CIVILCOURTDECREE";

    public static final Map<String, String> SALUTATION = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("-1", "Select");
            put("Mr", "Mr");
            put("Ms", "Ms");
            put("Mrs", "Mrs");
        }
    };

    public static final String PROPERTY_NOT_EXIST_ERR_CODE = "PTAX100";
    public static final String PROPERTY_DEACTIVATE_ERR_CODE = "PTAX101";
    public static final String PROPERTY_MARK_DEACTIVATE_ERR_CODE = "PTAX102";
    public static final String PROPERTY_ACTIVE_ERR_CODE = "PTAX103";
    public static final String PROPERTY_INACTIVE_ERR_CODE = "PTAX104";
    public static final String PROPERTY_EXEMPTED_ERR_CODE = "PTAX105";
    public static final String PROPERTY_DUPLICATE_ERR_CODE = "PTAX106";

    public static final String PROPERTY_NOT_EXIST_ERR_MSG_PREFIX = "Property with assessment no ";
    public static final String PROPERTY_NOT_EXIST_ERR_MSG_SUFFIX = " does not exist";
    public static final String PROPERTY_DEACTIVATE_ERR_MSG = "Property is decativated";
    public static final String PROPERTY_MARK_DEACTIVATE_ERR_MSG = "Property is marked for deactivation";
    public static final String PROPERTY_ACTIVE_NOT_EXISTS = "Active property does not exists for the given assessment number";
    public static final String PROPERTY_INACTIVE_ERR_MSG = "Property is not active for the given assessment number";
    public static final String PROPERTY_EXEMPTED_ERR_MSG = "Property is exempted";
    public static final String PROPERTY_DUPLICATE_ERR_MSG = "More than one assessment for the old assessment number: ";

    public static final String MARK_DEACTIVE = "MARK_DEACTIVE";
    public static final BigDecimal PENALTY_PERCENTAGE = new BigDecimal(2);
    public static final String STATUS_CODE_ASSESSED = "ASSESSED";
    public static final BigDecimal BIGDECIMAL_100 = new BigDecimal("100");
    public static final String BEANNAME_PROPERTY_TAX_BILLABLE = "propertyTaxBillable";

    // PDF Report constants
    public static final String REPORT_TEMPLATENAME_SPECIAL_NOTICE = "mainSpecialNotice";
    public static final String IMAGES_BASE_PATH = "/egi/resources/global/images/";
    public static final String IMAGE_CONTEXT_PATH = "/egi";
    public static final String REPORT_TEMPLATENAME_DEMANDNOTICE_GENERATION = "DemandBill";
    public static final String REPORT_TEMPLATENAME_INTEGRATED_DEMANDNOTICE_GENERATION = "integratedDemandBill";
    public static final String REPORT_TEMPLATENAME_REVISIONPETITION_HEARINGNOTICE = "mainHearingNotice";
    public static final String NOTICE_TYPE_REVISIONPETITION_HEARINGNOTICE = "Revision Petition Hearing Notice";
    public static final String REPORT_TEMPLATENAME_REVISIONPETITION_ENDORSEMENT = "revPetitionEndorsement";
    public static final String NOTICE_TYPE_REVISIONPETITION_ENDORSEMENT = "Revision Petition Endorsement";
    public static final String NOTICE_TYPE_REVISIONPETITION_ENDORSEMENT_PREFIX = "ENDORSEMENT";
    public static final String NOTICE_TYPE_REVISIONPETITION_SPECIALNOTICE = "Revision Petition SpecialNotice";
    public static final String NOTICE_TYPE_REVISIONPETITION_SPECIALNOTICE_PREFIX = "SPECIALNOTICE";
    public static final String NOTICE_TYPE_REVISIONPETITION_PROCEEDINGS_PREFIX = "PROCEEDINGS";
    public static final String REPORT_TEMPLATENAME_TRANSFER_CERTIFICATE = "mainMutationCertificate";
    public static final String REPORT_TEMPLATENAME_RP_SPECIAL_NOTICE = "mainRPSpecialNotice";
    public static final String REPORT_ESD_NOTICE_CORPORATION = "ESDNotice_Corporation";
    public static final String REPORT_ESD_NOTICE_MUNICIPALITY = "ESDNotice_Municipality";
    public static final String REPORT_DISTRESS_CORPORATION = "DistressWarrant_Corporation";
    public static final String REPORT_DISTRESS_MUNICIPALITY = "DistressWarrant_Municipality";
    public static final String REPORT_ARREAR_DEMAND_REGISTER = "arreardemand-register";
    public static final String REPORT_PT_TAX_REGISTER = "property-taxregister";
    public static final String REPORT_VLT_TAX_REGISTER = "vlt-taxregister";
    public static final String ADR_REPORT = "ADR Report";

    // Property Transfer related constants
    public static final String TRANSFER_FEE_COLLECTED = "Transfer Fee Collected";

    public static final String THIRD_PARTY_ERR_CODE_SUCCESS = "PTIS-REST-0";
    public static final String THIRD_PARTY_ERR_MSG_SUCCESS = "SUCCESS";
    public static final String THIRD_PARTY_ERR_CODE_INVALIDCREDENTIALS = "PTIS-REST-1";
    public static final String THIRD_PARTY_ERR_MSG_INVALIDCREDENTIALS = "Invaild Credentials";
    public static final String THIRD_PARTY_ERR_CODE_COMMUNICATION_FAILURE = "PTIS-REST-3";
    public static final String THIRD_PARTY_ERR_MSG_COMMUNICATION_FAILURE = "Communication Failure or Server may be down";
    public static final String THIRD_PARTY_ERR_CODE_ULBCODE = "PTIS-REST-4";
    public static final String THIRD_PARTY_ERR_MSG_ULBCODE = "Please Make Sure That First 4 Characters Of Assessment Must Match With ULB";
    public static final String THIRD_PARTY_ERR_CODE_PENDINGTAX = "PTIS-REST-5";
    public static final String THIRD_PARTY_ERR_MSG_PENDINGTAX = "No Pending Tax For This Assessment";
    public static final String THIRD_PARTY_ERR_CODE_TAXDTLS = "PTIS-REST-6";
    public static final String THIRD_PARTY_ERR_MSG_TAXDTLS = "Please Contact Municipality for Tax Details";
    public static final String THIRD_PARTY_ERR_CODE_NAME_DOOR = "PTIS-REST-7";
    public static final String THIRD_PARTY_ERR_MSG_NAME_DOOR = "Please enter name or door no";
    public static final String THIRD_PARTY_ERR_CODE_ASSESSMENT_NO_LEN = "PTIS-REST-8";
    public static final String THIRD_PARTY_ERR_MSG_ASSESSMENT_NO_LEN = "Assessment number length can not less than 10 digits";
    public static final String THIRD_PARTY_ERR_CODE_ASSESSMENT_NO_ULB = "PTIS-REST-9";
    public static final String THIRD_PARTY_ERR_MSG_ASSESSMENT_NO_ULB = "Assessment number first 4 digits should be ulbcode";
    public static final String THIRD_PARTY_ERR_CODE_ULBCODE_INVALID = "PTIS-REST-10";
    public static final String THIRD_PARTY_ERR_MSG_ULBCODE_INVALID = "Entered ulbid for search assesment or consumer number does not exist";
    public static final String THIRD_PARTY_ERR_CODE_DB_CONN_FAILED = "100";
    public static final String THIRD_PARTY_ERR_MSG_DB_CONN_FAILED = "DB Connection Problem";
    public static final String THIRD_PARTY_ERR_CODE_SAVE_FAILED = "103";
    public static final String THIRD_PARTY_ERR_MSG_SAVE_FAILED = "Fail to save your data";
    public static final String THIRD_PARTY_ERR_CODE_PAYMENT_UPDATE_FAILED = "104";
    public static final String THIRD_PARTY_ERR_MSG_PAYMENT_UPDATE_FAILED = "Payment update fail due other issues";
    public static final String THIRD_PARTY_ERR_CODE_PAYMENT_UPDATE_FAILED_WITH_INPUT = "106";
    public static final String THIRD_PARTY_ERR_MSG_PAYMENT_UPDATE_FAILED_WITH_INPUT = "Payment update fail due to input data issues";
    public static final String THIRD_PARTY_ERR_CODE_ASSESSMENT_NO_NOT_FOUND = "PTIS-REST-11";
    public static final String THIRD_PARTY_ERR_CODE_TRANSANCTIONID_REQUIRED = "PTIS-REST-25";
    public static final String THIRD_PARTY_ERR_MSG_TRANSANCTIONID_REQUIRED = "Invalid Request, No transaction ID is associated";
    public static final String THIRD_PARTY_ERR_CODE_TRANSANCTIONID_VALIDATE = "PTIS-REST-26";
    public static final String THIRD_PARTY_ERR_MSG_TRANSANCTIONID_VALIDATE = "Invalid Request, Used transaction ID is associated";
    public static final String THIRD_PARTY_ERR_MSG_TRANSANCTIONID_VALIDATE_1 = "Payment is rejected as the Transaction No. %s was already used on %s for Assessment %s.";
    public static final String THIRD_PARTY_ERR_MSG_ASSESSMENT_NO_NOT_FOUND = "Assessment number not found";
    public static final String CHECK_PROPERTY_CATEGORY = "Please enter correct property type";
    public static final String THIRD_PARTY_ERR_CODE_ASSESSMENT_NO_REQUIRED = "PTIS-REST-12";
    public static final String THIRD_PARTY_ERR_MSG_ASSESSMENT_NO_REQUIRED = "Assessment number is required";
    public static final String THIRD_PARTY_ERR_CODE_PAYMENT_MODE_REQUIRED = "PTIS-REST-13";
    public static final String THIRD_PARTY_ERR_MSG_PAYMENT_MODE_REQUIRED = "Payment mode is required";
    public static final String THIRD_PARTY_ERR_CODE_PAYMENT_MODE_INVALID = "PTIS-REST-14";
    public static final String THIRD_PARTY_ERR_MSG_PAYMENT_MODE_INVALID = "Payment mode is invalid";
    public static final String THIRD_PARTY_PAYMENT_MODE_CASH = "CASH";
    public static final String THIRD_PARTY_PAYMENT_MODE_CHEQUE = "CHEQUE";
    public static final String THIRD_PARTY_PAYMENT_MODE_DD = "DD";
    public static final String THIRD_PARTY_ERR_CODE_AADHAAR_NUMBER_EXISTS = "PTIS-REST-15";
    public static final String THIRD_PARTY_ERR_MSG_AADHAAR_NUMBER_EXISTS = "NRC Number {0} already exists";
    public static final String THIRD_PARTY_ERR_CODE_MOBILE_NUMBER_EXISTS = "PTIS-REST-16";
    public static final String THIRD_PARTY_ERR_MSG_MOBILE_NUMBER_EXISTS = "Mobile number {0} already exists";
    public static final String THIRD_PARTY_ERR_CODE_REVISIONPETITION_RECEIVEDON = "PTIS-REST-17";
    public static final String THIRD_PARTY_ERR_MSG_REVISIONPETITION_RECEIVEDON = "Revision petition received date required";
    public static final String THIRD_PARTY_ERR_CODE_REVISIONPETITION_RECEIVEDBY = "PTIS-REST-18";
    public static final String THIRD_PARTY_ERR_MSG_REVISIONPETITION_RECEIVEDBY = "Revision petition received user details required";
    public static final String THIRD_PARTY_ERR_CODE_REVISIONPETITION_RECEIVEDDETAIL = "PTIS-REST-19";
    public static final String THIRD_PARTY_ERR_MSG_REVISIONPETITION_RECEIVEDDETAIL = "Revision petition details are required";
    public static final String THIRD_PARTY_ERR_CODE_REVISIONPETITION_ALREADYINWORKFLOW = "PTIS-REST-20";
    public static final String THIRD_PARTY_ERR_MSG_REVISIONPETITION_ALREADYINWORKFLOW = "Selected property can not be objected,the property is in objected state or the property is in a different workflow";

    public static final String THIRD_PARTY_ERR_CODE_REVISIONPETITION_INVALID = "PTIS-REST-21";
    public static final String THIRD_PARTY_ERR_MSG_REVISIONPETITION_INVALID = "Invalid Revision petition number.";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_TRANSFEREENAME_MANDATORY = "PTIS-REST-22";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_TRANSFEREENAME_MANDATORY = "Owner details are mandatory, add atleast one transferee information. ";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_MUTATIONREASON_MANDATORY = "PTIS-REST-23";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_MUTATIONREASON_MANDATORY = "Please enter valid mutation reason code";

    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_SALEDETAIL_MANDATORY = "PTIS-REST-24";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_MUTATIONRDEEDDATE_MANDATORY = "PTIS-REST-25";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_MUTATIONDEEDNUMBER_MANDATORY = "PTIS-REST-26";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_SALEDETAIL_MANDATORY = "Please Enter Sale Details";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_MUTATIONRDEEDDATE_MANDATORY = "Registration Document Date should not be empty";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_MUTATIONDEEDNUMBER_MANDATORY = "Registration Document Number should not be empty";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_TRANSFEREE_MOBILENUMBERMANDATORY = "PTIS-REST-27";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_TRANSFEREE_MOBILENUMBERMANDATORY = "Please enter mobile number. ";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_TRANSFEREE_NAMEMANDATORY = "Please Enter Owner Name";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_TRANSFEREE_NAMEMANDATORY = "PTIS-REST-28";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_ALREADYINWORKFLOW = "PTIS-REST-29";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_ALREADYINWORKFLOW = "Transfer of Ownership not possible as the selected property is in a different workflow";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_TAXPENDING = "PTIS-REST-30";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_TAXPENDING = "Transfer of Ownership not possible.Tax due for the selected property";
    public static final String THIRD_PARTY_ERR_CODE_AADHAAR_NUMBER_NOTEXISTS = "PTIS-REST-31";
    public static final String THIRD_PARTY_ERR_MSG_AADHAAR_NUMBER_NOTEXISTS = "NRC Detail not found in uidai server for the given NRC Number ";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_REQUIREDDOCUMENTMISSING = "PTIS-REST-32";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_REQUIREDDOCUMENTMISSING = "Please attach relevant documents for property transfer. Type: ";
    public static final String THIRD_PARTY_ERR_CODE_PROPERTYTRANSFER_TRANSFEREE_GENDERMANDATORY = "PTIS-REST-33";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTYTRANSFER_TRANSFEREE_GENDERMANDATORY = "Please mention gender of transferee ";

    public static final String THIRD_PARTY_ERR_CODE_CHQDD_NO_REQUIRED = "PTIS-REST-34";
    public static final String THIRD_PARTY_ERR_MSG_CHQDD_NO_REQUIRED = "Cheque/DD number is required";
    public static final String THIRD_PARTY_ERR_CODE_CHQDD_DATE_REQUIRED = "PTIS-REST-35";
    public static final String THIRD_PARTY_ERR_MSG_CHQDD_DATE_REQUIRED = "Cheque/DD Date is required";

    public static final String THIRD_PARTY_ERR_CODE_BANKNAME_REQUIRED = "PTIS-REST-36";
    public static final String THIRD_PARTY_ERR_MSG_BANKNAME_REQUIRED = "Bank Name is required";

    public static final String THIRD_PARTY_ERR_CODE_BRANCHNAME_REQUIRED = "PTIS-REST-37";
    public static final String THIRD_PARTY_ERR_MSG_BRANCHNAME_REQUIRED = "Branch Name  is required";

    public static final String THIRD_PARTY_ERR_CODE_APPLICATION_NO_REQUIRED = "PTIS-REST-38";
    public static final String THIRD_PARTY_ERR_MSG_APPLICATION_NO_REQUIRED = "Application number is required";

    public static final String THIRD_PARTY_ERR_CODE_EXCESS_MUTATION_FEE = "PTIS-REST-40";
    public static final String THIRD_PARTY_ERR_MSG_EXCESS_MUTATION_FEE = "Mutation fee entered is excess";

    public static final String THIRD_PARTY_ERR_CODE_MUTATION_INVALID = "PTIS-REST-41";
    public static final String THIRD_PARTY_ERR_MSG_MUTATION_INVALID = "There are no mutations done for this assessment number";

    public static final String THIRD_PARTY_ERR_CODE_EXEMPTED_PROPERTY = "PTIS-REST-42";
    public static final String THIRD_PARTY_ERR_MSG_EXEMPTED_PROPERTY = "Property is Exempted";

    public static final String THIRD_PARTY_ERR_CODE_PROPERTY_TAX_ASSESSMENT_NOT_FOUND = "PTIS-REST-43";
    public static final String THIRD_PARTY_ERR_MSG_PROPERTY_TAX_ASSESSMENT_NOT_FOUND = "There is no Property Tax record found for this assessment";

    public static final String THIRD_PARTY_ERR_CODE_VACANTLAND_ASSESSMENT_NOT_FOUND = "PTIS-REST-44";
    public static final String THIRD_PARTY_ERR_MSG_VACANTLAND_ASSESSMENT_NOT_FOUND = "There is no Vacant Land record found for this assessment";

    public static final String THIRD_PARTY_ERR_CODE_WRONG_CATEGORY = "PTIS-REST-45";
    public static final String THIRD_PARTY_ERR_MSG_WRONG_CATEGORY = "Invalid Category";

    public static final String THIRD_PARTY_DEMAND_AMOUNT_GREATER_CODE = "PTIS-REST-46";
    public static final String THIRD_PARTY_DEMAND_AMOUNT_GREATER_MSG = "Paid Amount is greater than Total Amount to be paid";
    public static final String THIRD_PARTY_DEMAND_AMOUNT_LESSER_MSG = "Paid Amount is lesser than Total Amount to be paid";

    public static final String THIRD_PARTY_AMOUNT_HAVING_DECIMALS_CODE = "PTIS-REST-47";
    public static final String THIRD_PARTY_AMOUNT_HAVING_DECIMALS_MSG = "Paid amount in decimal is not accepted.";

    public static final String THIRD_PARTY_DOCS_UPLOAD_SUCCESS_CODE = "PTIS-REST-48";
    public static final String THIRD_PARTY_DOCS_UPLOAD_SUCCESS_MSG = "Document(s) uploaded successfully.";
    
    public static final String THIRD_PARTY_ERR_CODE_ADVANCE_LIMIT_EXCEEDED = "PTIS-REST-49";
    public static final String THIRD_PARTY_ERR_MSG_ADVANCE_LIMIT_EXCEEDED = "Payment is rejected because Advance amount is beyond 10 installments.";
    
    public static final String THIRD_PARTY_APPLICATION_DETAIL_SUCCESS_CODE = "PTIS-REST-50";
    public static final String THIRD_PARTY_APPLICATION_DETAIL_SUCCESS_MSG = "Application number is incorrect.";
    
    public static final String THIRD_PARTY_VALID_APPLICATION_NO_CODE = "PTIS-REST-51";
    public static final String THIRD_PARTY_VALID_APPLICATION_NO_MSG = "Please enter valid Application number.";
    
    public static final String REST_API_DISABLED_CODE = "PTIS-REST-52";
    public static final String REST_API_DISABLED_MSG = "The API is temporarily unavailable.";
    
    public static final String TOTAL_AMOUNT = "amount";
    public final static String PAID_BY = "paidBy";

    // Collection modes List
    public static final Map<Character, String> COLL_MODES_MAP = new HashMap<Character, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(COLLECTION_TYPE_COUNTER, "Counter");
            put(COLLECTION_TYPE_FIELDCOLLECTION, "Field");
            put(COLLECTION_TYPE_ONLINECOLLECTION, "Online");
        }
    };

    public static final LinkedHashMap<String, String> HEARING_TIMINGS = new LinkedHashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("9.00 AM", "9.00 AM");
            put("9.30 AM", "9.30 AM");
            put("10.00 AM", "10.00 AM");
            put("10.30 AM", "10.30 AM");
            put("11.00 AM", "11.00 AM");
            put("11.30 AM", "11.30 AM");
            put("12.00 PM", "12.00 PM");
            put("12.30 PM", "12.30 PM");
            put("01.00 PM", "01.00 PM");
            put("01.30 PM", "01.30 PM");
            put("02.00 PM", "02.00 PM");
            put("02.30 PM", "02.30 PM");
            put("03.00 PM", "03.00 PM");
            put("03.30 PM", "03.30 PM");
            put("04.00 PM", "04.00 PM");
            put("04.30 PM", "04.30 PM");
            put("05.00 PM", "05.00 PM");
            put("05.30 PM", "05.30 PM");
            put("06.00 PM", "06.00 PM");

        }
    };

    // Application Types

    public static final String APPLICATION_TYPE_NEW_ASSESSENT = "New_Assessment";
    public static final String APPLICATION_TYPE_ALTER_ASSESSENT = "Alter_Assessment";
    public static final String APPLICATION_TYPE_BIFURCATE_ASSESSENT = "Bifuracate_Assessment";
    public static final String APPLICATION_TYPE_TRANSFER_OF_OWNERSHIP = "Transfer_of_Ownership";
    public static final String APPLICATION_TYPE_COLLECT_TAX = "Collect_Tax";
    public static final String APPLICATION_TYPE_DEMAND_BILL = "Generate_demand_bill";
    public static final String APPLICATION_TYPE_TAX_EXEMTION = "Tax_Exemption";
    public static final String APPLICATION_TYPE_DEMOLITION = "Demolition";
    public static final String APPLICATION_TYPE_VACANCY_REMISSION = "Vacancy_Remission";
    public static final String APPLICATION_TYPE_VACANCY_REMISSION_APPROVAL = "Vacancy_Remission_Approval";
    public static final String APPLICATION_TYPE_MEESEVA_TRANSFER_OF_OWNERSHIP = "Meeseva_Transfer_of_Ownership";
    public static final String APPLICATION_TYPE_GRP = "General_Revision_Petition";
    public static final String APPLICATION_TYPE_EDIT_DEMAND = "Edit_demand";
    public static final String APPLICATION_TYPE_ADD_DEMAND = "Add_demand";
    public static final String APPLICATION_TYPE_EDIT_OWNER = "Edit_owner";
    public static final String APPLICATION_TYPE_EDIT_MOBILE_NO = "Edit_mobileno";
    public static final String APPLICATION_TYPE_EDIT_DOOR_NO = "Edit_doorno";
    public static final String APPLICATION_TYPE_EDIT_COLLECTION = "Edit_Collection";
    public static final String APPLICATION_TYPE_COURT_VERDICT = "Court_Verdict";
    public static final String APPLICATION_TYPE_MODIFY_DATA_ENTRY = "Edit_Data_Entry";
    public static final String APPLICATION_TYPE_MEESEVA_GRP = "Meeseva_General_Revision_Petition";
    public static final String APPLICATION_TYPE_MEESEVA_RP = "Meeseva_Revision_Petition";
    public static final String APPLICATION_TYPE_AMALGAMATION = "Amalgamation";
    public static final String APPLICATION_TYPE_MARKASCOURTCASE = "Mark_As_Court_Case";
    public static final String APPLICATION_TYPE_DEACTIVATE = "Property Deactivation";
    
    
    public static final String DOORNO_EDIT_MODE = "editDoorNo";
    public static final String MOBILENO_EDIT_MODE = "editMobileNo";
    public static final String OWNERDETAILS_EDIT_MODE = "editOwnerDetails";

    // AppConfig values
    public static final String PROPERTYTAX_WORKFLOWDEPARTEMENT = "PROPERTYTAXDEPARTMENTFORWORKFLOW";
    public static final String PROPERTYTAX_WORKFLOWDESIGNATION = "PROPERTYTAXDESIGNATIONFORWORKFLOW";
    public static final String PROPERTYTAX_WORKFLOWDESIGNATION_FOR_CSCOPERATOR = "PROPERTYTAXDESIGNATIONFORCSCOPERATORWORKFLOW";
    public static final String PROPERTYTAX_WORKFLOWDEPARTEMENT_FOR_CSCOPERATOR = "PROPERTYTAXDEPARTMENTFORCSCOPERATORWORKFLOW";
    public static final String PROPERTYTAX_ROLEFORNONEMPLOYEE = "PROPERTYTAXROLEFORNONEMPLOYEE";
    public static final String APPLICATION_TYPE_REVISION_PETITION = "Revision_Petition";
    public static final String PT_WORKFLOWDESIGNATION_MOBILE = "PTIS_DESIGNATIONFORWF_MOBILE";
    public static final String DESIGNATION_FOR_THIRDPARTY_FULLTRANSFER_WF = "PT_DESIGNATION_FOR_THIRDPARTY_FULLTRANSFER";
    public static final String DEPARTMENT_FOR_THIRDPARTY_FULLTRANSFER_WF = "PT_DEPARTMENT_FOR_THIRDPARTY_FULLTRANSFER";

    // Action targets
    public static final String TARGET_WORKFLOW_ERROR = "workFlowError";
    public static final String TARGET_TAX_DUES = "taxdues";
    public static final String PROPERTY_VALIDATION = "propertyValidation";
    public static final String PROPERTY_VALIDATION_FOR_SPRING = "propertyValidationForSpring";

    public static final String THIRD_PARTY_PHOTO_OF_ASSESSMENT_CODE = "1";
    public static final String THIRD_PARTY_BUILDING_PERMISSION_COPY_CODE = "2";
    public static final String THIRD_PARTY_ATTESTED_COPY_PROPERTY_DOCUMENT_CODE = "3";
    public static final String THIRD_PARTY_NON_JUDICIAL_STAMP_PAPERS_CODE = "4";
    public static final String THIRD_PARTY_NOTARIZED_AFFIDAVIT_CUM_IDEMNITY_BOND_CODE = "5";
    public static final String THIRD_PARTY_DEATH_CERTIFICATE_COPY_CODE = "6";

    public static final String THIRD_PARTY_CONTENT_TYPE = "application/{0}";

    public static final Character SOURCEOFDATA_APPLICATION = 'A';
    public static final Character SOURCEOFDATA_MIGRATION = 'M';
    public static final Character SOURCEOFDATA_DATAENTRY = 'D';
    public static final Character SOURCEOFDATA_MEESEWA = 'T';
    public static final Character SOURCEOFDATA_ONLINE = 'O';
    public static final Character SOURCEOFDATA_SURVEY = 'S';
    public static final Character SOURCEOFDATA_ESEVA = 'E';
    public static final Character SOURCEOFDATA_CARD = 'D';
    public static final Character SOURCEOFDATA_WARDSECRETARY = 'W';

    public static final String ALTERATION_OF_ASSESSMENT = "Alteration of Assessment";
    public static final String BIFURCATION_OF_ASSESSMENT = "Bifurcation of Assessment";
    public static final String AMALGAMATION_OF_ASSESSMENT = "Amalgamation of Assessment";
    public static final String GRP_OF_ASSESSMENT = "General Revision Petition of Assessment";

    public static final String CATEGORY_VACANT_LAND = "VACANTLAND";
    public static final String CATEGORY_STATE_GOVT = "STATE_GOVT";
    public static final String CATEGORY_CENTRAL_GOVT = "CENTRAL_GOVT";
    public static final String CATEGORY_RESIDENTIAL = "RESIDENTIAL";
    public static final String CATEGORY_NON_RESIDENTIAL = "NON_RESIDENTIAL";
    public static final String CATEGORY_MIXED = "MIXED";

    public static final String PROP_MUTATION_RSN = "TRANSFER";
    public static final String FILESTORE_MODULE_NAME = "PTIS";

    // Vacancy Remission
    public static final String VR_STATUS_REJECTION_ACK_GENERATED = "Rejection Acknowledgement Generated";
    public static final String VR_STATUS_WORKFLOW = "IN_WORKFLOW";
    public static final String VR_STATUS_APPROVED = "APPROVED";
    public static final String VR_STATUS_REJECTED = "REJECTED";
    public static final String NOTICE_TYPE_VACANCYREMISSION_PREFIX = "VACANREMISSION";
    public static final String VR_SPECIALNOTICE_TEMPLATE = "mainVRProceedings";
    public static final String NOTICE_TYPE_VACANCYREMISSION_PROCEEDINGS = "Vacancy Remission Proceedings";
    public static final String VR_STATUS_NOTICE_GENERATED = "Specialnotice Generated";
    public static final String VR_CURR_STATUS = "VacancyRemission:Assistant Approved";
    public static final String NOTICE_TYPE_VRPROCEEDINGS = "VR Proceedings";
    public static final String VR_STATUS_ASSISTANT_FORWARDED = "Assistant Forwarded";
    public static final String VR_STATUS_COMMISSIONER_FORWARD_PENDING = "Commissioner Forward Pending";
    public static final String VR_APP_STATUS_REJECTED = "VacancyRemission:Rejected";
    public static final String VR_STATUS_MONTHLY_UPDATE = "Monthly Update Pending";
    public static final String NOTICE_TYPE_EXEMPTIONPROCEEDINGS = "Tax Exemption Proceedings";

    public static final String VACANTLAND_PROPERTY_CATEGORY = "VACANTLAND";
    public static final String MEESEVA_OPERATOR_ROLE = "MeeSeva Operator";
    public static final String CITIZEN_ROLE = "CITIZEN";
    
    // Ward Secretary
    public static final String WARDSCRETARY_OPERATOR_ROLE = "WardSecretary Operator";
    public static final String WARDSECRETARY_SOURCE_CODE = "source";
    public static final String WARDSECRETARY_TRANSACTIONID_CODE = "transactionId";
    public static final String WARDSECRETARY_WSPORTAL_REQUEST = "wsPortalRequest";

    // Status for Meseva
    public static final String STATUS_REJECTED = "Rejected";
    public static final String STATUS_APPROVED = "Approved";
    public static final String STATUS_OPEN = "Open";
    public static final String STATUS_INPROGRESS = "InProgress";
    public static final String MEESEVA_REDIRECT_URL = "/meeseva/generatereceipt?transactionServiceNumber=";

    //WS View URLs
    public static final String WS_VIEW_PROPERT_BY_APP_NO_URL = "%s/ptis/view/viewProperty-viewForm.action?applicationNo=%s&applicationType=%s";
    
    
    public static final String SEARCH_RESULT_COUNT = "500";

    // Nature of task
    public static final String NATURE_NEW_ASSESSMENT = "New Assessment";
    public static final String NATURE_ALTERATION = "Addition/Alteration";
    public static final String NATURE_BIFURCATION = "Bifurcation";
    public static final String NATURE_TITLE_TRANSFER = "Title Transfer";
    public static final String NATURE_REVISION_PETITION = "Revision Petition";
    public static final String NATURE_TAX_EXEMPTION = "Tax Exemption";
    public static final String NATURE_VACANCY_REMISSION = "Vacany Remission";
    public static final String NATURE_VACANCY_REMISSION_APPROVAL = "Vacancy Remission Approval";
    public static final String NATURE_GENERAL_REVISION_PETITION = "General Revision Petition";
    public static final String NATURE_REGISTERED_TRANSFER = "Registered Transfer";
    public static final String NATURE_FULL_TRANSFER = "Full Transfer";
    public static final String NATURE_PARTIAL_TRANSFER = "Partial Transfer";
    public static final String CITY_GRADE_CORPORATION = "Corp";
    public static final String VACANTLAND_MIN_CUR_CAPITALVALUE = "500";
    public static final String WF_STATE_UD_REVENUE_INSPECTOR_APPROVAL_PENDING = "UD Revenue Inspector Approval Pending";
    public static final String WF_STATE_UD_REVENUE_INSPECTOR_APPROVED = "UD Revenue Inspector Approved";
    public static final String WF_STATE_ASSISTANT_MUNICIPAL_COMMISSIONER_APPROVAL_PENDING = "Assistant Municipal Commissioner Approval Pending";
    public static final String WF_STATE_DEPUTY_MUNICIPAL_COMMISSIONER_APPROVAL_PENDING = "Deputy Municipal Commissioner Approval Pending";
    public static final String WF_STATE_ADDITIONAL_MUNICIPAL_COMMISSIONER_APPROVAL_PENDING = "Additional Municipal Commissioner Approval Pending";
    public static final String WF_STATE_MUNICIPAL_COMMISSIONER_APPROVAL_PENDING = "Municipal Commissioner Approval Pending";
    public static final String WF_STATE_MUNICIPAL_COMMISSIONER_APPROVED = "Municipal Commissioner Approved";
    public static final String NATURE_COURT_VERDICT = "Court Verdict";
    public static final String NATURE_OF_USAGE_RESIDENCE = "Residence";
    public static final String GRADE_NAGAR_PANCHAYAT = "NP";
    public static final String NATURE_WRITE_OFF = "Write Off";
    // Tax Rates

    public static final Map<String, String> TAX_RATES = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(DEMANDRSN_CODE_VACANT_TAX, DEMANDRSN_STR_VACANT_TAX);
            put(DEMANDRSN_CODE_EDUCATIONAL_TAX, DEMANDRSN_STR_EDUCATIONAL_TAX);
            put("GEN_TAX_RESD", "General Tax Residential");
            put(DEMANDRSN_CODE_LIBRARY_CESS, DEMANDRSN_STR_LIBRARY_CESS);
            put("GEN_TAX_NR", "General Tax Non Residential");
        }
    };

    public static final List<String> TAX_RATES_TEMP = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(DEMANDRSN_CODE_PRIMARY_SERVICE_CHARGES);
            add("SEW_TAX_RESD");
            add("SEW_TAX_NR");

        }
    };

    // Tax collection SMS
    public static final String STR_INSTRUMENTTYPE_CHEQUE = "Your cheque no.";
    public static final String STR_INSTRUMENTTYPE_DD = "Your DD no.";
    public static final String STR_WITH_AMOUNT = " with amount :";
    public static final String STR_FOR_SUBMISSION = "/- has been submitted for Property tax collection. Amount received will be adjusted against the assessment no : ";
    public static final String STR_REALIZATION = " subject to the realization of instrument.";
    public static final String STR_FOR_CASH = "We have received a property tax cash payment of Rs.";
    public static final String STR_FOR_CASH_ADJUSTMENT = "/- and it would be adjusted against your assessment no.";

    public static final String GUARDIAN_RELATION_WIFE = "WIFE";

    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT = "CENTRAL_GOVT";
    public static final String OWNERSHIP_TYPE_CENTRAL_GOVT_STR = "Central Government";
    public static final String OWNERSHIP_TYPE_COURT_CASE = "COURT_CASE";
    public static final String OWNERSHIP_TYPE_COURT_CASE_STR = "Court case";
    public static final String OWNERSHIP_TYPE_PRIVATE_DEFAULTERS_STR = "Private(excluding court case)";
    public static final Map<String, String> OWNERSHIP_OF_PROPERTY_FOR_DEFAULTERS_REPORT = new TreeMap<String, String>() {
        private static final long serialVersionUID = -8758751964576480520L;

        {
            put(OWNERSHIP_TYPE_CENTRAL_GOVT, OWNERSHIP_TYPE_CENTRAL_GOVT_STR);
            put(OWNERSHIP_TYPE_STATE_GOVT, OWNERSHIP_TYPE_STATE_GOVT_STR);
            put(OWNERSHIP_TYPE_COURT_CASE, OWNERSHIP_TYPE_COURT_CASE_STR);
            put(OWNERSHIP_TYPE_PRIVATE, OWNERSHIP_TYPE_PRIVATE_DEFAULTERS_STR);

        }
    };

    public static final String CURRENTYEAR_FIRST_HALF = "Current 1st Half";
    public static final String CURRENTYEAR_SECOND_HALF = "Current 2nd Half";
    public static final String ARREARS = "Arrears";

    public static final String REVISIONPETITION_STATUS_CODE = "RP";

    public static final String APPCONFIG_DIGITAL_SIGNATURE = "PTIS_DIGITAL_SIGNATURE_REQUIRED";
    public static final String APPCONFIG_MAUD_INTEGRATION_REQUIRED = "PTIS_MAUD_INTEGRATION_REQUIRED";

    public static final String APPCONFIG_CLIENT_SPECIFIC_DMD_BILL = "IS_CLIENT_SPECIFIC_DEMANDBILL";

    public static final String SERVICE_CODE_VACANTLANDTAX = "VLT";
    public static final String SERVICE_CODE_PROPERTYTAX = "PT";
    public static final String SERVICE_CODE_MUTATION = "PTMF";

    public static final String MUTATION_TYPE_REGISTERED_TRANSFER = "REGISTERED TRANSFER";
    public static final String PARTT = "Transfer of ownership of part of property which needs to be registered in Registration and Stamps Office";
    public static final String FULLTT = "Transfer of ownership of entire property which needs to be registered in Registration and Stamps Office";
    public static final String ALL_READY_REGISTER = "Transfer of ownership of property which is already registered in Registration and Stamps Office";

    public static final String TTTEXT = " of the registration is completed";
    public static final String TTTEXTEND = " in Registration and Stamps Office";

    public static final String REG_DEPT_WEBSERVICE_USERNAME = "CMDAService1";
    public static final String REG_DEPT_WEBSERVICE_PASSWORD = "CMDAService1";

    public static final String MOBILE_PAYMENT_INCORRECT_BILL_DATA = "Bill data is incorrect";
    public static final String APPROVAL_COMMENTS_SUCCESS = "Property has been successfully forwarded.";
    public static final String CREATE_CURRENT_STATE_BILL_COLLECTOR_APPROVED = "Create:Bill Collector Approved";
    public static final String UD_REVENUE_INSPECTOR_APPROVAL_PENDING = "UD Revenue Inspector Approval Pending";
    public static final String MODIFY_CURRENT_STATE_BILL_COLLECTOR_APPROVED = "Alter:Bill Collector Approved";

    // Collection services
    public static final String CATEGORY_TYPE_PROPERTY_TAX = "PT";
    public static final String CATEGORY_TYPE_VACANTLAND_TAX = "VLT";
    public static final String PROP_DEACT_RSN = "DEACTIVATE";
    // urls
    public String WTMS_TAXDUE_RESTURL = "%s/wtms/rest/watertax/due/byptno/%s";
    public String WTMS_TOTALDEMAND_RESTURL = "%s/wtms/rest/watertax/totaldemandamount/";
    public String WTMS_CONNECT_DTLS_RESTURL = "%s/wtms/rest/watertax/connectiondetails/byptno/%s";
    public String WTMS_AMALGAMATE_WATER_CONNECTIONS_URL = "%s/wtms/rest/watertax/updateConnectionForAmulgamation";
    public String STMS_TAXDUE_RESTURL = "%s/stms/rest/seweragetax/dues/%s";
    public String WTMS_TAXDUE_WITH_DATE_RESTURL = "%s/wtms/rest/watertax/due/byptnoanddate/%s,%s";
    public String LCMS_LEGALCASE_DETAILS_RESTURL = "%s/lcms/legalcase/ajax-caseDetailsByCaseNumber";
    public String COUNCIL_RESOLUTION_RESTURL = "%s/council/councilresolutiondetails?%s";
    public String WTMS_CLOSURE_STATUS_URL = "%s/wtms/rest/watertax/getClosureStatus/%s";

    public static final String TRANSACTION_TYPE_CREATE = "Create";
    public static final String TRANSACTION_TYPE_DEMOLITION = "Demolition";

    public static final String PROPERTY_MODE_CREATE = "create";
    public static final String PROPERTY_MODE_MODIFY = "modify";

    public static final String PAYMENT_TYPE_PARTIALLY = "Partially";
    public static final String PAYMENT_TYPE_FULLY = "Fully";
    public static final String PAYMENT_TYPE_ADVANCE = "Advance";

    public static final String COLLECION_BILLING_SERVICE_PT = "Property Tax";
    public static final String COLLECION_BILLING_SERVICE_VLT = "Property Tax (On Land)";
    public static final String COLLECION_BILLING_SERVICE_WTMS = "Water Charges";

    public static final String PROPERTY_TAX_INDEX_NAME = "propertytax";
    public static final String COLLECTION_INDEX_NAME = "receipts";
    public static final String WATER_TAX_INDEX_NAME = "waterchargeconsumer";
    public static final String BILL_COLLECTOR_INDEX_NAME = "billcollector";
    public static final String COLLECTION_ACHIEVEMENTS_INDEX_NAME = "collectionachievements";
    public static final String PROPERTY_TAX_INDEX_PREFIX = "propertytaxdcb_";
    public static final String PTYEARWISEDCBSTARTYEAR = "PTIS_YEAR_WISE_DCB_DATE";

    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";

    public static final String DASHBOARD_GROUPING_DISTRICTWISE = "district";
    public static final String DASHBOARD_GROUPING_ULBWISE = "ulb";
    public static final String DASHBOARD_GROUPING_REGIONWISE = "region";
    public static final String DASHBOARD_GROUPING_GRADEWISE = "grade";
    public static final String DASHBOARD_GROUPING_WARDWISE = "ward";
    public static final String DASHBOARD_GROUPING_CITYWISE = "city";
    public static final String DASHBOARD_GROUPING_BILLCOLLECTORWISE = "billcollector";
    public static final String DASHBOARD_GROUPING_ALLWARDS = "allwards";
    public static final String DASHBOARD_GROUPING_SERVICEWISE = "service";
    public static final String DASHBOARD_GROUPING_REVENUEINSPECTORWISE = "revenueinspector";
    public static final String DASHBOARD_GROUPING_REVENUEOFFICERWISE = "revenueofficer";
    public static final String DASHBOARD_GROUPING_FUNCTIONARYWISE = "functionary";

    public static final String DASHBOARD_PROPERTY_TYPE_PRIVATE = "Private";
    public static final String DASHBOARD_PROPERTY_TYPE_VACANT_LAND = "Vacant Land";
    public static final String DASHBOARD_PROPERTY_TYPE_STATE_GOVT = "State Government";
    public static final String DASHBOARD_PROPERTY_TYPE_CENTRAL_GOVT = "CENTRAL_GOVT";
    public static final String DASHBOARD_PROPERTY_TYPE_COURTCASES = "Courtcases";
    public static final String DASHBOARD_PROPERTY_TYPE_BUILT_UP = "BuiltUp";

    public static final String DASHBOARD_USAGE_TYPE_ALL = "ALL";

    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String DAY = "day";

    public static final String APPCONFIG_REASSIGN = "PTIS_REASSIGN_REQUIRED";
    public static final String APPCONFIG_ENDORSEMENT = "PTIS_ENDORSEMENT_REQUIRED";
    public static final String APPCONFIG_APPLICATION_DETAILS_REST_API = "PTIS_APPLICATION_DETAILS_REST_API_ENABLED";

    public static final List<String> DASHBOARD_PROPERTY_TYPE_CENTRAL_GOVT_LIST = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(OWNERSHIP_TYPE_CENTRAL_GOVT_333_STR);
            add(OWNERSHIP_TYPE_CENTRAL_GOVT_50_STR);
            add(OWNERSHIP_TYPE_CENTRAL_GOVT_75_STR);
        }
    };

    public static final List<String> DASHBOARD_BUILT_UP_PROPERTY_TYPES = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {

            add(OWNERSHIP_TYPE_PRIVATE_STR);
            add(OWNERSHIP_TYPE_STATE_GOVT_STR);
            add(OWNERSHIP_TYPE_CENTRAL_GOVT_333_STR);
            add(OWNERSHIP_TYPE_CENTRAL_GOVT_50_STR);
            add(OWNERSHIP_TYPE_CENTRAL_GOVT_75_STR);
            add(OWNERSHIP_TYPE_EWSHS);
        }
    };

    // Recovery notices
    public static final String NOTICE_TYPE_ESD = "ESD Notice";
    public static final String REPORT_INVENTORY_NOTICE_CORPORATION = "InventoryNotice_Corporation";
    public static final String REPORT_INVENTORY_NOTICE_MUNICIPALITY = "InventoryNotice_Municipality";
    public static final String NOTICE_TYPE_INVENTORY = "Inventory Notice";
    public static final String NOTICE_TYPE_DISTRESS = "Distress Notice";
    public static final String VALUATION_CERTIFICATE = "Valuation Certificate";
    public static final String NOTICE_TYPE_OCCUPIER = "Occupier Notice";
    public static final String VALUATION_CERTIFICATE_CORPORATION = "ValuationCertificate_Corporation";
    public static final String VALUATION_CERTIFICATE_MUNICIPALITY = "ValuationCertificate_Municipality";
    public static final String NOTICE_TYPE_OC = "Ownership Certificate";
    public static final String NOTICE_TYPE_ENDORSEMENT = "Endorsement Notice";
    public static final String NOTICE_TYPE_SURVEY_COMPARISON = "Survey Comparison Document";
    // esd notice section act and distress section act for corporation use the
    // same constant
    public static final String CORPORATION_ESD_NOTICE_SECTION_ACT = "(Issued under Section 269(2) of Municipal Corporations Act,1955 (formerly GHMC Act, 1955)";

    public static final String MUNICIPALITY_ESD_NOTICE_SECTION_ACT = "(Issued under Section 91 of A.P. Municipalities Act 1965)";
    public static final String MUNICIPALITY_DISTRESS_NOTICE_SECTION_ACT = "(Issued under Rule 30(3) of Taxation & Finance Rules appended to the A.P. Municipalities Act, 1965)";

    // exempted proceedings based on exempted reasons
    public static final String NOTICE_TYPE_EXEMPTION = "exemption";
    public static final String EXEMPTION_PUBLIC_WORSHIP = "PUBLIC_WORSHIP";
    public static final String EXEMPTION_CHOULTRY = "CHOULTRY";
    public static final String EXEMPTION_EXSERVICE = "EXSERVICE";
    public static final String EXEMPTION_EDU_INST = "EDU_INST";
    public static final String EXEMPTION_PENSIONER = "PENSIONER";
    public static final String EXEMPTION_NGO = "NGO";
    public static final String EXEMPTION_MUNICIPALITY_PROPERTY = "MUN_PROP";
    public static final String REPORT_NGO_EXEMPTION_NOTICE = "mainNGOExemptionNotice";
    public static final String REPORT_RETIRED_EXEMPTION_NOTICE = "mainRetiredAssociationExemptionNotice";
    public static final String REPORT_EDU_INST_EXEMPTION_NOTICE = "mainEduInstExemptionNotice";
    public static final String REPORT_EXSRVICE_EXEMPTION_NOTICE = "mainExServiceManExemptionNotice";
    public static final String REPORT_CHOULTRY_EXEMPTION_NOTICE = "mainChoultryExemptionNotice";
    public static final String REPORT_PUBLIC_WORSHIP_EXEMPTION_NOTICE = "mainPublicWorshipExemptionNotice";
    public static final String REPORT_OWNERSHIP_CERTIFICATE_MULTIPLE = "OwnershipCertificate_MultipleOwner";
    public static final String REPORT_OWNERSHIP_CERTIFICATE_SINGLE = "OwnershipCertificate_SingleOwner";
    public static final String REPORT_MUNICIPALITY_PROPERTY_EXEMPTION = "mainMunicipalExemption";
    public static final List<String> RECOVERY_NOTICETYPES = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(NOTICE_TYPE_ESD);
            add(NOTICE_TYPE_INVENTORY);
            add(NOTICE_TYPE_DISTRESS);
            add(NOTICE_TYPE_OC);
            add(NOTICE_TYPE_OCCUPIER);
            add(VALUATION_CERTIFICATE);
        }
    };

    public static final String DOCUMENT_TYPE_PATTA_CERTIFICATE = "Patta Certificate";
    public static final String DOCUMENT_TYPE_MRO_PROCEEDINGS = "MRO Proceedings";
    public static final String DOCUMENT_TYPE_WILL_DEED = "Will Deed";
    public static final String DOCUMENT_TYPE_DECREE_DOCUMENT = "Decree Document";
    public static final String DOCUMENT_TYPE_REGD_DOCUMENT = "Registered Document";
    public static final String DOCUMENT_TYPE_PHOTO_PROPERTY_HOLDER = "Photo of Property With Holder";
    public static final String DOCUMENT_NAME_REGD_DOCUMENT = "Registered Document";
    public static final String DOCUMENT_NAME_PATTA_CERTIFICATE = "Patta Certificate (Issued by Revenue Department)";
    public static final String DOCUMENT_NAME_SUCCESSION_DOCUMENT = "Succession Document";
    public static final String DOCUMENT_NAME_LEASE_DOCUMENT = "Registered Lease Deed";
    public static final String DOCUMENT_NAME_REGD_WILL_DOCUMENT = "Registered Will Document";
    public static final String DOCUMENT_NAME_UNREGD_WILL_DOCUMENT = "Un-registered Will Document";
    public static final String DOCUMENT_NAME_DECREE_BY_CIVILCOURT = "Decree by Civil Court";
    public static final String DOCUMENT_NAME_NOTARY_DOCUMENT = "Un-registered Document / Notary document";
    public static final List<String> ASSESSMENT_DOCUMENT_NAMES = ImmutableList.of(DOCUMENT_NAME_PATTA_CERTIFICATE,
            DOCUMENT_NAME_REGD_WILL_DOCUMENT, DOCUMENT_NAME_UNREGD_WILL_DOCUMENT, DOCUMENT_NAME_DECREE_BY_CIVILCOURT,
            DOCUMENT_TYPE_REGD_DOCUMENT, DOCUMENT_NAME_NOTARY_DOCUMENT);
    public static final List<String> COMMISSIONER_DESIGNATIONS = ImmutableList.of(ASSISTANT_COMMISSIONER_DESIGN,
            ZONAL_COMMISSIONER_DESIGN, DEPUTY_COMMISSIONER_DESIGN, ADDITIONAL_COMMISSIONER_DESIGN, COMMISSIONER_DESGN);
    public static final List<String> ASSISTANT_DESIGNATIONS = ImmutableList.of(JUNIOR_ASSISTANT, SENIOR_ASSISTANT);
    public static final String INTEGRATED_BILL = "Integrated Bill";

    // Source of Application
    public static final String SOURCE_CSC = "CSC";
    public static final String SOURCE_MEESEVA = "MEESEVA";
    public static final String SOURCE_MOBILE = "MOBILE";
    public static final String SOURCE_SYSTEM = "SYSTEM";
    public static final String SOURCE_ONLINE = "ONLINE";
    public static final String SOURCE_SURVEY = "SURVEY";

    public static final String APPURTENANT_PROPERTY = "AppurTenantProperty";
    public static final String ANONYMOUS_USER = "Anonymous";
    public static final String WATER_TAX_DUES = "totalTaxDue";
    public static final String UNDER_WTMS_WF = "isInWorkFlow";

    public static final String SUPER_STRUCTURE = "Super Structure";
    public static final String GRP_APP_STATUS_REJECTED = "GRP:Rejected";
    public static final String RP_APP_STATUS_REJECTED = "RP:Rejected";

    public static final List<String> DEMAND_REASONS_FOR_REBATE_CALCULATION = ImmutableList.of(
            DEMANDRSN_CODE_GENERAL_TAX, DEMANDRSN_CODE_DRAINAGE_TAX, DEMANDRSN_CODE_SCAVENGE_TAX,
            DEMANDRSN_CODE_WATER_TAX, DEMANDRSN_CODE_LIGHT_TAX, DEMANDRSN_CODE_VACANT_TAX, DEMANDRSN_CODE_LIBRARY_CESS,
            DEMANDRSN_CODE_EDUCATIONAL_TAX);

    public static final List<String> PROPERTY_TYPE_SERVICE_CODES = ImmutableList.of("PT", "VLT");
    public static final List<String> SERVICE_TYPES = ImmutableList.of(APPLICATION_TYPE_NEW_ASSESSENT,
            APPLICATION_TYPE_ALTER_ASSESSENT, APPLICATION_TYPE_GRP, APPLICATION_TYPE_REVISION_PETITION);

    public static final String NOTARY_DOCUMENT_OWNER = "The Holder Of The Premises";

    public static final List<String> PROPERTY_TYPE_GOVT_LIST = ImmutableList.of(OWNERSHIP_TYPE_STATE_GOVT,
            OWNERSHIP_TYPE_CENTRAL_GOVT_333, OWNERSHIP_TYPE_CENTRAL_GOVT_50, OWNERSHIP_TYPE_CENTRAL_GOVT_75);
    public static final String REVISION_PETETION = "REVISION_PETETION";
    public static final String VACANCY_REMISSION = "VACANCY_REMISSION";
    public static final String GENERAL_REVISION_PETETION_APPTYPE = "GENERAL_REVISION_PETETION";

    public static final String NOTICE_TEMPLATE_COMPARISON_NOTICE = "mainGIS_comparitiveReport";

    public static final String DOCUMENT_TYPE_THIRD_PARTY_SURVEY = "Third Party Verification Copy";

    public static final List<String> NON_VACANT_TAX_DEMAND_CODES = ImmutableList.of(DEMANDRSN_CODE_DRAINAGE_TAX,
            DEMANDRSN_CODE_GENERAL_TAX, DEMANDRSN_CODE_SCAVENGE_TAX, DEMANDRSN_CODE_WATER_TAX,
            DEMANDRSN_CODE_LIGHT_TAX);

    public static final List<String> NON_VACANT_TAX_DEMAND_REASONS = ImmutableList.of(DEMANDRSN_STR_DRAINAGE_TAX,
            DEMANDRSN_STR_GENERAL_TAX, DEMANDRSN_STR_SCAVENGE_TAX, DEMANDRSN_STR_WATER_TAX, DEMANDRSN_STR_LIGHT_TAX);

    public static final List<String> ASSESSMENT_DOCUMENT_NAMES_RP = ImmutableList.of(DOCUMENT_NAME_PATTA_CERTIFICATE,
            DOCUMENT_NAME_REGD_WILL_DOCUMENT, DOCUMENT_NAME_UNREGD_WILL_DOCUMENT, MUTATIONRS_DECREE_BY_CIVIL_COURT,
            DOCUMENT_TYPE_REGD_DOCUMENT);

    public static final List<String> APPLICATION_TYPES = ImmutableList.of("New_Assessment", "Alter_Assessment");
    public static final List<String> APPLICATION_STATUS = ImmutableList.of("Open", "Closed", "Approved", "Cancelled");

    public static final Map<String, String> SURVEY_APPLICATION_TYPES = new TreeMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(PROP_CREATE_RSN, "New Property");
            put(PROPERTY_MODIFY_REASON_ADD_OR_ALTER, "Addition/Alteration");

        }
    };

    public static final String APPCONFIG_GIS_THIRDPARTY_CHECKBOX_REQUIRED = "PTIS_GIS_THIRDPARTY_CHECKBOX_REQUIRED";
    public static final String APPCONFIG_DEMAND_VOUCHER_GENERATION_REQUIRED = "PTIS_DEMAND_VOUCHER_GENERATION_REQUIRED";
    public static final String APPCONFIG_PT_DEMAND_VOUCHER_GLCODES = "PT_DEMAND_VOUCHER_GLCODES";

    public static final String DOCUMENT_TYPE_PHOTO_OF_ASSESSMENT = "Photo Of Assessment";

    public static final String APPCONFIG_PTIS_BULKBILL_GENERATION_SCHEDULER_ENABLED = "PTIS_BULKBILL_GENERATION_SCHEDULER_ENABLED";

    public static final Map<String, String> DOCTYPEBYMUTATIONREASON = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(CIVILCOURTDECREE, MUTATIONRS_DECREE_BY_CIVIL_COURT);
            put(MUTATION_REASON_CODE_PARTISION, DOCUMENT_TYPE_REGD_DOCUMENT);
            put(MUTATION_REASON_CODE_SALE, DOCUMENT_TYPE_REGD_DOCUMENT);
            put(MUTATION_REASON_CODE_TITLEDEED, DOCUMENT_TYPE_REGD_DOCUMENT);
            put(MUTATION_REASON_CODE_REGISTERED, DOCUMENT_NAME_REGD_WILL_DOCUMENT);
            put(MUTATION_REASON_CODE_SUCCESSION, DOCUMENT_NAME_SUCCESSION_DOCUMENT);
            put(MUTATION_REASON_CODE_RENT, DOCUMENT_NAME_LEASE_DOCUMENT);
            put(MUTATION_REASON_CODE_UNREGISTEREDWILL, MUTATIONRS_UNREG_WILL);
        }
    };

    public static final Map<String, String> APPLICATIONTYPEBYNATUREOFTASK = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(NATURE_NEW_ASSESSMENT, APPLICATION_TYPE_NEW_ASSESSENT);
            put(NATURE_ALTERATION, APPLICATION_TYPE_ALTER_ASSESSENT);
            put(NATURE_BIFURCATION, APPLICATION_TYPE_BIFURCATE_ASSESSENT);
            put(APPLICATION_TYPE_DEMOLITION, APPLICATION_TYPE_DEMOLITION);
            put(NATURE_TAX_EXEMPTION, APPLICATION_TYPE_TAX_EXEMTION);
            put(APPLICATION_TYPE_AMALGAMATION, APPLICATION_TYPE_AMALGAMATION);
        }
    };

    public static final Map<String, String> NATUREOFTASKBYADDITIONALRULE = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(NEW_ASSESSMENT, NATURE_NEW_ASSESSMENT);
            put(ADDTIONAL_RULE_ALTER_ASSESSMENT, NATURE_ALTERATION);
            put(ADDTIONAL_RULE_BIFURCATE_ASSESSMENT, NATURE_BIFURCATION);
            put(DEMOLITION, APPLICATION_TYPE_DEMOLITION);
            put(EXEMPTION, NATURE_TAX_EXEMPTION);
            put(AMALGAMATION, APPLICATION_TYPE_AMALGAMATION);
        }
    };

    // Court Verdict Constants

    public static final String APPROVAL_COMMENT = "approvalComent";
    public static final String WF_ACTION = "workFlowAction";
    public static final String APPROVAL_POSITION = "approvalPosition";
    public static final String LOGGED_IN_USER = "loggedUserIsEmployee";
    public static final String SUCCESS_MSG = "successMessage";
    public static final String CV_SUCCESS_FORM = "courtVerdict-success";
    public static final String COURT_VERDICT = "courtVerdict";
    public static final String CURRENT_STATE = "currentState";
    public static final String STATE_TYPE = "stateType";
    public static final String CV_FORM = "courtVerdict-form";
    public static final String CV_VIEW = "courtVerdict-view";
    public static final String PROPERTY = "property";
    public static final String ACTION = "action";
    public static final String CURRENT_DESG = "currentDesignation";
    public static final String HISTORY_MAP = "historyMap";
    public static final String STATE = "state";
    public static final String STATE_AWARE_ID = "stateAwareId";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String RE_ASSESS = "RE_ASSESS";
    public static final String UPDATE_DEMAND_DIRECTLY = "UPDATE_DMND_DIRECTLY";
    public static final String DEMAND_DETAIL_LIST = "demandDetailList";
    public static final String ENDORSEMENT_NOTICE = "endorsementNotices";
    public static final String COURTCASE = "COURT_CASE";

    // WriteOff
    public static final String FULL_WRITEOFF = "Full WriteOff";
    public static final String PARTIAL_WRITEOFF = "Partial WriteOff";

    public static final String WO_SUCCESS_FORM = "writeoff-success";
    public static final String WO_FORM = "writeoff-form";
    public static final String WO_VIEW = "writeoff-view";
    public static final String WRITE_OFF = "WRITE_OFF";
    public static final String WRITEOFF_CODE = "WRITEOFF";
    public static final String WRITEOFF = "WriteOff";
    public static final String NOTICE_TYPE_WRITEOFFROCEEDINGS = "Write Off Proceedings";
    public static final String WO_SPECIALNOTICE_TEMPLATE = "mainWriteOffProceedings";
    public static final String NOTICE_TYPE_WOPROCEEDINGS = "WO Proceedings";
    public static final String ACTIVE = "ACTIVE";
    public static final String COUNCIL_ERROR = "COUNCIL RESOLUTION DOES NOT EXIST";
    public static final String INPROGRESS = "INPROGRESS";
    
    public static final String NO_ACTION = "NA";
    public static final String ZERO_DEMAND = "ZERO_DEMAND";
    public static final String APPLICATION_TYPE = "applicationType";
    //Rejection notice
    public static final String NOTICE_TYPE_REJECTION = "Rejection Notice";
    public static final String REJECTION_NOTICE_TEMPLATE = "RejectionNotice";
    public static final String WFLOW_ACTION_STEP_REJECT_TO_CANCEL = "Reject To Cancel";
    public static final String WF_STATE_REJECTED_TO_CANCEL = "Rejected to Cancel";
    
  //Appeal petition 
    public static final String APPLICATION_TYPE_APPEAL_PETITION = "Appeal_Petition";
    public static final String NATURE_APPEALPETITION = "Appeal Petition";
    public static final String WFLOW_ACTION_APPEALPETITION = "APPEAL PETITION";
    public static final String APPEAL_HEARINGCOMPLETED = "Appeal:Hearing completed";
    public static final String APPEAL_INSPECTIONVERIFIED = "Appeal:Inspection verified";
    public static final String APPEAL_INSPECTION_COMPLETE = "Appeal:Inspection completed";
    public static final String APPEAL_WF_REGISTERED = "Appeal:Registered";
    public static final String APPEAL_APP_STATUS_REJECTED = "Appeal:Rejected";
    public static final String APPEAL_NEW = "Appeal:NEW";
    public static final String APPEAL_CREATED = "Appeal:CREATED";
    public static final String REPORT_TEMPLATENAME_APPEAL_SPECIAL_NOTICE = "mainAppealProceeding";
    public static final String APPEALPETITION_CODE = "APPEAL PETITION";
    public static final String APPEAL_PETITION = "APPEAL_PETETION";
    public static final String NOTICE_TYPE_APPEALPROCEEDINGS = "Appeal Petition Proceedings";
    public static final String NOTICE_TYPE_APPEALPETITION_HEARINGNOTICE = "Appeal Petition Hearing Notice";
    public static final String NOTICE_TYPE_APPEALPETITION_ENDORSEMENT = "Appeal Petition Endorsement";
    public static final String APPEAL = "Appeal";
    
    //Red Notice
    public static final String NOTICE_TYPE_RED_NOTICE = "Red Notice";
    public static final String TEMPLATENAME_MAIN_RED_NOTICE = "mainRedNotice";
  //Appeal Report
    public static final String APPEAL_FINANCIAL_START_DATE = "01/04/2019";
    public static final String APPEAL_REPORT_TEMPLATE = "appealPetition-registerreport";
    
    //courtcase writeoff search application   
    public static final List<String> APPLICATIONTYPES = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(NATURE_COURT_VERDICT);
            add(NATURE_WRITE_OFF);
        }
    };
    
    public static final List<String> APPLICATION_STATUS_LIST = ImmutableList.of("Open", "Closed", "All");

}