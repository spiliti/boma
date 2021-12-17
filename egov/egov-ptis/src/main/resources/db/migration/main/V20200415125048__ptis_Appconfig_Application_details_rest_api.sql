------ Appconfig to enable/disable Application Details Rest API 
INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'PTIS_APPLICATION_DETAILS_REST_API_ENABLED', 'Key to enable/disable Application Property deatils Rest API for Property Tax',0, (select id from eg_module where name='Property Tax')); 

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'), (SELECT id FROM EG_APPCONFIG WHERE KEYNAME='PTIS_APPLICATION_DETAILS_REST_API_ENABLED'), current_date,'N',0);
