update eg_city set name ='Mapepe City Council', localname = 'Mapepe';
insert into eg_filestoremap (id, filestoreid, filename, contenttype, version, createddate, lastmodifieddate, lastmodifiedby, createdby)  values (1, 1, 'mapepe_logo.png', 'image',  1, now(), now(), 1, 1);
update eg_citypreferences set municipalitylogo = 1;



