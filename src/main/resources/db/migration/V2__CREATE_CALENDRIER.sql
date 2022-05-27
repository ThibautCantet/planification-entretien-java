create table rendezVous
(
    id          int     not null primary key,
    recruteurId int     not null,
    description varchar not null,
    horaire     timestamp
);
