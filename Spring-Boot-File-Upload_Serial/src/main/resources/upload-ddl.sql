CREATE TABLE document_metadata
(
  id  serial NOT NULL,
  name text NOT NULL,
  content_type text NOT NULL,
  content_size integer NOT NULL,
  path text NOT NULL,
  CONSTRAINT document_metadata_pkey PRIMARY KEY (id)
);

--need to change type of id to serial then it will create sequence generator on database part