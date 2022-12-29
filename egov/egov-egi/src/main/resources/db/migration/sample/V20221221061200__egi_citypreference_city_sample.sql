insert into eg_filestoremap (id, filestoreid, filename, contenttype, version, createddate, lastmodifieddate, lastmodifiedby, createdby)  values (1, 1, 'mapepe_logo.png', 'image',  1, now(), now(), 1, 1);
insert into eg_citypreferences (id, municipalitylogo, createdby,createddate, lastmodifiedby, lastmodifieddate, version, municipalityname) values (1,1,1, now(),1,now(), 0, 'Mapepe City Council');
update eg_city set name ='Mapepe City Council', preferences = 1, localname = 'Mapepe' where domainurl = 'localahost';
INSERT INTO eg_city (domainurl, name, localname, id, active, version, createdby, lastmodifiedby, createddate, lastmodifieddate, code, districtcode, districtname, longitude, latitude, preferences) VALUES ('41.63.0.133', 'Mapepe City Council', 'Mapepe', nextval('seq_eg_city'), true, 0, 1, 1, '2010-01-01 00:00:00', '2015-01-01 00:00:00', '0001',  NULL, NULL, NULL, NULL, 1);
INSERT INTO eg_city (domainurl, name, localname, id, active, version, createdby, lastmodifiedby, createddate, lastmodifieddate, code, districtcode, districtname, longitude, latitude, preferences) VALUES ('mapepe2.zm4a.org', 'Mapepe City Council', 'Mapepe', nextval('seq_eg_city'), true, 0, 1, 1, '2010-01-01 00:00:00', '2015-01-01 00:00:00', '0001',  NULL, NULL, NULL, NULL, 1);
update eg_citypreferences set recaptchapk = '6LfidggTAAAAANDSoCgfkNdvYm3Ugnl9HC8_68o0', recaptchapub = '6LfidggTAAAAADwfl4uOq1CSLhCkH8OE7QFinbVs';


      Column      |            Type             | Modifiers
------------------+-----------------------------+-----------
 domainurl        | character varying(128)      | not null
 name             | character varying(256)      | not null
 localname        | character varying(256)      |
 id               | bigint                      | not null
 active           | boolean                     |
 version          | bigint                      |
 createdby        | numeric                     |
 lastmodifiedby   | numeric                     |
 createddate      | timestamp without time zone |
 lastmodifieddate | timestamp without time zone |
 code             | character varying(4)        |
 districtcode     | character varying(10)       |
 districtname     | character varying(50)       |
 longitude        | double precision            |
 latitude         | double precision            |
 preferences      | numeric                     |
 regionname       | character varying(50)       |
 grade            | character varying(50)       |
 lgdcode          | character varying(10)       |
 districtlgdcode