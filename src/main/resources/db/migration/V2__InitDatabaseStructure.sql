create sequence if not exists course_item_sequence start with 1 increment by 1;
create sequence if not exists lesson_item_sequence start with 1 increment by 1;

create table if not exists course_table (
  course_currency varchar(3) not null,
  course_price numeric(5,2) not null,
  course_start_date date not null,
  version integer not null,
  course_id bigint not null,
  course_label varchar(50) not null,
  primary key (course_id)
);

create table if not exists lesson_table (
  lesson_course_id bigint,
  lesson_id bigint not null,
  lesson_start_time timestamp(6) not null,
  lesson_lecturer_id varchar(20),
  lesson_name varchar(50) not null,
  primary key (lesson_id)
);

create table if not exists user_course_table (
  course_id bigint not null,
  student_id varchar(20) not null,
  unique (student_id, course_id)
);

create table if not exists user_table (
  version integer not null,
  last_visit_at timestamp(6),
  registered_at timestamp(6) not null,
  user_id varchar(20) not null,
  user_name varchar(20) not null,
  user_role varchar(20) not null,
  user_surname varchar(20) not null,
  user_pwd varchar(255) not null,
  primary key (user_id)
);

alter table if exists lesson_table
  add constraint lesson_course_fk
    foreign key (lesson_course_id)
    references course_table;

alter table if exists lesson_table
  add constraint lesson_lecturer_fk
  foreign key (lesson_lecturer_id)
  references user_table;

alter table if exists user_course_table
  add constraint courses_user_fk
  foreign key (course_id)
  references course_table;

alter table if exists user_course_table
  add constraint student_courses_fk
  foreign key (student_id)
  references user_table;