create table candidat
(
    id                uuid         not null primary key,
    language          varchar(255) not null,
    email             varchar(255) not null,
    experienceInYears int          not null
);

create table recruteur
(
    id                uuid         not null primary key,
    language          varchar(255) not null,
    email             varchar(255) not null,
    experienceInYears int          not null
);

create table entretien
(
    id          uuid not null primary key,
    recruteurId uuid not null,
    candidatId  uuid not null,
    horaire     timestamp
);
