alter table student add constraint age check (age>16);
alter table student alter column name set not null;
alter table student add constraint name_constraint unique(name);
alter table faculty add constraint name_color unique(name, color);
alter table student alter column age set default 20;