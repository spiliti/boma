insert into eg_roleaction(roleid,actionid) (select (SELECT id FROM eg_role WHERE name = 'Bank Collection Operator'), id 
from eg_action where name in ('Search Trade License','Trade License Bill Collect','paylicensefees'));