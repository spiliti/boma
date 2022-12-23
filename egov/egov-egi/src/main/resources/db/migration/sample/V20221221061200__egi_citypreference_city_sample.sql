update eg_city set name ='Mapepe City Council', localname = 'Mapepe';
insert into eg_filestoremap values (1, 1, 'mapepe_logo.png', 'image', now(), now(), 1, 1);
update egcitypreferences set municipalitylogo = 1;
