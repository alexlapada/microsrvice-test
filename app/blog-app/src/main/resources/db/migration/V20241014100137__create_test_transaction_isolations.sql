create table if not exists tts_job
(
    id      varchar(36) primary key,
    name    varchar,
    version int
);

create table if not exists job
(
    id      varchar(36) primary key,
    name    varchar,
    status  varchar,
    version int,
    tts_job_id varchar(36),
    constraint fk_job_tts_job_id foreign key (tts_job_id) references tts_job(id)
);

create table if not exists slide_metadata
(
    id       varchar(36) primary key,
    title    varchar,
    duration bigint,
    version  int,
    job_id varchar(36),
    constraint fk_slide_metadata_job_id foreign key (job_id) references job(id)
);

insert into tts_job (id, name, version) VALUES ('a2ec1980-35a8-4c98-b0ea-8b1a7e4a88c4', 'Tts Job 1', 1);

insert into job (id, name, status, version, tts_job_id) VALUES
                                                ('e2ec1980-35a8-4c88-b0ea-8b1a7e4a88c4', 'Job 1', 'RUNNING', 1, 'a2ec1980-35a8-4c98-b0ea-8b1a7e4a88c4');
insert into slide_metadata (id, title, duration, version, job_id) VALUES
                                                                      ('8e89ab87-54e5-4986-935d-d72ce98a59cd', 'Slide 1', 0, 1, 'e2ec1980-35a8-4c88-b0ea-8b1a7e4a88c4'),
                                                                      ('e3890777-8c05-45d7-b696-64abb289bd53', 'Slide 2', 0, 1, 'e2ec1980-35a8-4c88-b0ea-8b1a7e4a88c4'),
                                                                      ('e3890777-8c05-45d7-b696-64abb289bd63', 'Slide 3', 0, 1, 'e2ec1980-35a8-4c88-b0ea-8b1a7e4a88c4'),
                                                                      ('e3890777-8c05-45d7-b696-64abb289bd73', 'Slide 4', 0, 1, 'e2ec1980-35a8-4c88-b0ea-8b1a7e4a88c4');