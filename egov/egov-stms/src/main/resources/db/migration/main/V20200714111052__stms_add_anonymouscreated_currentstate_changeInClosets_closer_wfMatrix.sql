INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate) 
VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'SewerageApplicationDetails', 'Anonymous  Created', NULL, NULL, NULL, 'CHANGEINCLOSETS', 'NEW', 'Junior/Senior Assistance approval pending', 'Junior Assistant,Senior Assistant', 'ANONYMOUSCREATED','Forward', NULL, NULL, '2020-07-14', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate) 
VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'SewerageApplicationDetails', 'Anonymous  Created', NULL, NULL, NULL, 'CLOSESEWERAGECONNECTION', 'NEW', 'Junior/Senior Assistance approval pending', 'Junior Assistant,Senior Assistant', 'ANONYMOUSCREATED','Forward', NULL, NULL, '2020-07-14', '2099-04-01');
