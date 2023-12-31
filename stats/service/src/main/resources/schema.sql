DROP TABLE IF EXISTS public.stats;

CREATE table IF NOT EXISTS public.stats (
 stats_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	app varchar(40) NOT NULL,
	uri varchar(255) NOT NULL,
	ip varchar(40) NOT NULL,
	visited timestamp without time zone NOT NULL,
	CONSTRAINT stats_pk PRIMARY KEY (stats_id)
);