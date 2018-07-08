DROP TABLE IF EXISTS document_metadata;
CREATE TABLE IF NOT EXISTS document_metadata (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(200) DEFAULT NULL,
  content_type varchar(200) DEFAULT NULL,
  content_size int(11),
  path varchar(200) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
