CREATE TABLE country (
    id SMALLINT AUTO_INCREMENT UNIQUE,
    name character varying(50) NOT NULL,
    CONSTRAINT country_pkey PRIMARY KEY (id),
    version INT DEFAULT 0
);

CREATE TABLE hotel (
    id SMALLINT AUTO_INCREMENT UNIQUE,
    name character varying(50) NOT NULL,
    phone character varying(35) NOT NULL,
    stars SMALLINT,
    country_id SMALLINT NOT NULL,
    CONSTRAINT hotel_pkey PRIMARY KEY (id),
    CONSTRAINT hotel_country FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE SET NULL ON UPDATE SET NULL,
    version INT DEFAULT 0
);

CREATE TABLE tour_type (
    id SMALLINT AUTO_INCREMENT UNIQUE,
    name character(60) NOT NULL,
    CONSTRAINT tour_type_pkey PRIMARY KEY (id),
    version INT DEFAULT 0
);

CREATE TABLE "user" (
    id SMALLINT AUTO_INCREMENT UNIQUE,
    login character(55) NOT NULL,
    password character(55) NOT NULL,
    name character(55),
    surname character(55),
    CONSTRAINT "user_pkey" PRIMARY KEY (id),
    version INT DEFAULT 0
);

CREATE TABLE tour (
    id SMALLINT AUTO_INCREMENT UNIQUE,
    photo character(255) NOT NULL,
    date date NOT NULL,
    duration SMALLINT NOT NULL,
    description text NOT NULL,
    type_id SMALLINT NOT NULL,
    coast double NOT NULL,
    country_id SMALLINT NOT NULL,
    hotel_id SMALLINT,
    CONSTRAINT tour_pkey PRIMARY KEY (id),
    CONSTRAINT tour_type FOREIGN KEY (type_id) REFERENCES tour_type(id) ON DELETE SET NULL ON UPDATE SET NULL,
    CONSTRAINT tour_country FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE SET NULL ON UPDATE SET NULL,
    CONSTRAINT tour_hotel FOREIGN KEY (hotel_id) REFERENCES hotel(id) ON DELETE SET NULL ON UPDATE SET NULL,
    version INT DEFAULT 0
);

CREATE TABLE review (
    id SMALLINT AUTO_INCREMENT UNIQUE,
    content text NOT NULL,
    tour_id SMALLINT NOT NULL,
    user_id SMALLINT NOT NULL,
    CONSTRAINT review_pkey PRIMARY KEY (id),
    CONSTRAINT review_tour FOREIGN KEY (tour_id) REFERENCES tour(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT review_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE SET NULL ON UPDATE SET NULL,
    version INT DEFAULT 0
);

CREATE TABLE user_has_tour (
    user_id SMALLINT NOT NULL,
    tour_id SMALLINT NOT NULL,
    CONSTRAINT user_has_tour_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT user_has_tour_tour FOREIGN KEY (tour_id) REFERENCES tour(id) ON DELETE CASCADE ON UPDATE CASCADE,
    version INT DEFAULT 0
);
