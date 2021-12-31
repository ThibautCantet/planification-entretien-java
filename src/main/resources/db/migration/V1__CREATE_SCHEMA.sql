create table candidat
(
    id                int          not null primary key,
    language          varchar(255) not null,
    email             varchar(255) not null,
    experienceInYears int          not null
);

create table recruteur
(
    id                int          not null primary key,
    language          varchar(255) not null,
    email             varchar(255) not null,
    experienceInYears int          not null
);

create table entretien
(
    id          int not null primary key,
    recruteurId int not null,
    candidatId  int not null,
    horaire     timestamp
);
