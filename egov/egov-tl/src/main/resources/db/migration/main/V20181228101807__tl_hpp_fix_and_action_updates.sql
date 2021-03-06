UPDATE eg_action set name='TL_NEW_LICENSE_APPROVAL_FORM' WHERE name='View Trade License for Approval';
UPDATE eg_action set name='TL_DIGITAL_SIGNATURE' WHERE name='digitalSignature-TLTransitionWorkflow';
UPDATE eg_action set name='TL_DOWLOAD_DIGI_SIGNED_CERTIFICATE' WHERE name='digitalSignature-TLDownloadSignDoc';
UPDATE eg_action set name='TL_DEMAND_GENERATION' WHERE name='demand-generate';
UPDATE eg_action set name='TL_CREATE_DOCUMENT_TYPE' WHERE name='Create License Document Type';
UPDATE eg_action set name='TL_UPDATE_DOCUMENT_TYPE' WHERE name='Edit Document Type';
UPDATE eg_action set name='TL_SEARCH_DOCUMENT_TYPE' WHERE name='Search License Document Type';
UPDATE eg_action set name='TL_VIEW_DOCUMENT_TYPE' WHERE name='View Document Type';
DELETE FROM eg_roleaction WHERE ACTIONID=(SELECT id FROM eg_action WHERE name='Delete Document Type');
DELETE FROM eg_feature_action WHERE action=(SELECT id FROM eg_action WHERE name='Delete Document Type');
DELETE FROM eg_action WHERE name='Delete Document Type';
DELETE FROM eg_roleaction WHERE ACTIONID=(SELECT id FROM eg_action WHERE name='viewclosurelicense');
DELETE FROM eg_feature_action WHERE action=(SELECT id FROM eg_action WHERE name='viewclosurelicense');
DELETE FROM eg_action WHERE name='viewclosurelicense';
DELETE FROM eg_roleaction WHERE ACTIONID=(SELECT id FROM eg_action WHERE name='updateclosurelicense');
DELETE FROM eg_feature_action WHERE action=(SELECT id FROM eg_action WHERE name='updateclosurelicense');
DELETE FROM eg_action WHERE name='updateclosurelicense';
UPDATE eg_action set name='TL_DEMAND_NOTICE_SEARCH' WHERE name='License Demand Notice Search';
UPDATE eg_action set name='TL_DEMAND_NOTICE_GENERATE' WHERE name='Generate License Demand Notice';
UPDATE eg_action set name='TL_NEW_LICENSE_APPLICATION_ACKNOWLEDGEMENT' WHERE name='newTradeLicense-printAck';
DELETE FROM eg_roleaction WHERE ACTIONID=(SELECT id FROM eg_action WHERE name='generatedemandforlicense');
DELETE FROM eg_feature_action WHERE action=(SELECT id FROM eg_action WHERE name='generatedemandforlicense');
DELETE FROM eg_action WHERE name='generatedemandforlicense';
UPDATE eg_action set name='TL_LICENSE_FEE_COLLECTION' WHERE name='paylicensefees';
DELETE FROM eg_roleaction WHERE ACTIONID=(SELECT id FROM eg_action WHERE name='showclosureform');
DELETE FROM eg_feature_action WHERE action=(SELECT id FROM eg_action WHERE name='showclosureform');
DELETE FROM eg_action WHERE name='showclosureform';
UPDATE eg_action set name='TL_LEGACY_LICENSE_VIEW' WHERE name='View License';
UPDATE eg_action set name='TL_CHECK_UNIQUE_OLD_LICENSE_NO' WHERE name='OldLicenseNumber';
DELETE FROM eg_roleaction WHERE ACTIONID=(SELECT id FROM eg_action WHERE name='demand-generatemissing');
DELETE FROM eg_feature_action WHERE action=(SELECT id FROM eg_action WHERE name='demand-generatemissing');
DELETE FROM eg_action WHERE name='demand-generatemissing';
UPDATE eg_action set name='TL_LICENSE_ONLINE_FEE_PAYMENT' WHERE name='License Online Payment';
UPDATE eg_action set name='TL_LICENSE_VIEW_DCB_DETAILS' WHERE name='License DCB View';
DELETE FROM eg_roleaction WHERE ACTIONID=(SELECT id FROM eg_action WHERE name='saveclosure');
DELETE FROM eg_feature_action WHERE action=(SELECT id FROM eg_action WHERE name='saveclosure');
DELETE FROM eg_action WHERE name='saveclosure';
UPDATE eg_action set name='TL_REASSIGN_PROCESS_OWNER' WHERE name='Reassignment';
UPDATE eg_action set name='TL_VIEW_SUPPORT_DOCS' WHERE name='View Support Documents';
UPDATE eg_action set name='TL_BULK_DIGITAL_SIGNATURE' WHERE name='License Bulk Digital Signature';
UPDATE eg_action set name='TL_GENERATE_PROVISIONAL_CERTIFICATE' WHERE name='Generate Provisional Certificate';
UPDATE eg_action set name='TL_LICENSE_ACKNOWLEDGEMENT' WHERE name='License Acknowledgement';
UPDATE eg_action set name='TL_SAVE_LICENSE_CHANGES',url='/newtradelicense/newTradeLicense-save.action' WHERE name='savetradelicense';
UPDATE eg_action set name='TL_CLOSURE_GENERATE_NOTICES' WHERE name='Generate Closure Notices';
UPDATE eg_action set name='TL_CLOSURE_GENERATE_ENDORSEMENT_NOTICE' WHERE name='Generate Closure Endorsement Notice';
UPDATE eg_action set name='TL_CLOSURE_DOWNLOAD_ENDORSEMENT_NOTICE' WHERE name='Download Closure Endorsement Notice';
UPDATE eg_action set name='TL_CLOSURE_DIGITAL_SIGNATURE' WHERE name='Closure DigitalSigned';
UPDATE eg_action set name='TL_CLOSURE_APPLICATION' WHERE name='Create License Closure';
UPDATE eg_action set name='TL_CLOSURE_APPLICATION_UPDATE' WHERE name='Show License Closure';
UPDATE eg_action set name='TL_CLOSURE_APPLICATION_FORWARD' WHERE name='Forward License Closure';
UPDATE eg_action set name='TL_CLOSURE_APPLICATION_APPROVAL' WHERE name='Approve License Closure';
UPDATE eg_action set name='TL_CLOSURE_APPLICATION_REJECT' WHERE name='Reject License Closure';
UPDATE eg_action set name='TL_CLOSURE_APPLICATION_CANCEL' WHERE name='Cancel License Closure';
UPDATE eg_action set name='TL_CLOSURE_APPLICATION_SUCCESS' WHERE name='View License Success';

UPDATE eg_action set name='TL_VIEW_LICENSE' WHERE name='View License Application';
UPDATE eg_action set name='TL_SHOW_LICENSE' WHERE name='Show license application';
UPDATE eg_action set name='TL_VIEW_PROVISIONAL_CERTIFICATE' WHERE name='License provisional certificate';

UPDATE eg_action set name='TL_REPORT_DCB' WHERE name='License DCB Report';
UPDATE eg_action set name='TL_REPORT_DCB_DOWNLOAD' WHERE name='DCB Report Download';
UPDATE eg_action set name='TL_REPORT_DCB_GRAND_TOTAL' WHERE name='DCB Report Grand Total';

UPDATE eg_action set name='TL_REPORT_BASE_REGISTER' WHERE name='License Base Register Report';
UPDATE eg_action set name='TL_REPORT_BASE_REGISTER_DOWNLOAD' WHERE name='Base Register Report Download';
UPDATE eg_action set name='TL_REPORT_BASE_REGISTER_GRAND_TOTAL' WHERE name='Base Register Report Grand Total';

UPDATE eg_action set name='TL_REPORT_YEARWISE_DCB' WHERE name='License Yearwise DCB Report';
UPDATE eg_action set name='TL_REPORT_YEARWISE_DCB_DOWNLOAD' WHERE name='Download Year Wise Report';
UPDATE eg_action set name='TL_REPORT_YEARWISE_DCB_GRAND_TOTAL' WHERE name='Year Wise Report Gand Total';

UPDATE eg_action set name='TL_REPORT_DAILY_COLLECTION',url='/reports/dailycollection' WHERE name='Daily Collection Report';
