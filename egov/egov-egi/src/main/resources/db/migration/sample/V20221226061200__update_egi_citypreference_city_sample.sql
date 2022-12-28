insert into eg_filestoremap (id, filestoreid, filename, contenttype, version, createddate, lastmodifieddate, lastmodifiedby, createdby)  values (1, 1, 'mapepe_logo.png', 'image',  1, now(), now(), 1, 1);
insert into eg_citypreferences (id, municipalitylogo, createdby,createddate, lastmodifiedby, lastmodifieddate, version, municipalityname) values (1,1,1, now(),1,now(), 0, 'Mapepe City Council');
update eg_city set name ='Mapepe City Council', preferences = 1, localname = 'Mapepe' where domainurl = 'localahost';



