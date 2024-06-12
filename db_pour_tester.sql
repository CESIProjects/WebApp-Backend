--
-- Pour récupérer un export: Backup de la base de pg_admin en clic droit sur celle-ci puis sélectionner plain_text et dans options #1-> only schema. Récap :
-- In pgAdmin, right click on the database and click Backup.
-- Enter an appropriate path and filename (i.e. /some/path/my_script.sql).
-- Select Plain as the format in the format dropdown.
-- Go to the Dump Options #1 tab and check "Only schema".
-- Then click Backup. Then click Done.

-- PostgreSQL database dump
--

-- Dumped from database version 14.11 (Ubuntu 14.11-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.11 (Ubuntu 14.11-0ubuntu0.22.04.1)

-- Started on 2024-03-07 15:05:35 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 16386)
-- Name: additional_content; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.additional_content (
    id SERIAL PRIMARY KEY,
    link character(300) NOT NULL,
    type character(50) NOT NULL,
    size integer NOT NULL,
    name character(80) NOT NULL
);


ALTER TABLE public.additional_content OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16397)
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id SERIAL PRIMARY KEY,
    name character(60) DEFAULT NULL::bpchar NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16403)
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment (
    id SERIAL PRIMARY KEY,
    title character(120) NOT NULL,
    content character(1500) NOT NULL,
    post_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.comment OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16418)
-- Name: favorites; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.favorites (
    id SERIAL PRIMARY KEY,
    post_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.favorites OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16423)
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post (
    id SERIAL PRIMARY KEY,
    title character(120) NOT NULL,
    content character(1500) NOT NULL,
    publication_date date NOT NULL,
    popular boolean NOT NULL,
    user_id integer NOT NULL,
    category_id integer NOT NULL
);


ALTER TABLE public.post OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16430)
-- Name: post_additional_content; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post_additional_content (
    post_id integer NOT NULL,
    comment_id integer NOT NULL
);


ALTER TABLE public.post_additional_content OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16435)
-- Name: reply; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reply (
    comment_id integer NOT NULL,
    reply_id integer NOT NULL
);


ALTER TABLE public.reply OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16440)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."users" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    role VARCHAR(20) NOT NULL,
	UNIQUE(username),
  	UNIQUE(email)
);


ALTER TABLE public."users" OWNER TO postgres;

--
-- TOC entry 3262 (class 2606 OID 16450)
-- Name: comment fk_comment_post_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fk_comment_post_id FOREIGN KEY (post_id) REFERENCES public.post(id) NOT VALID;


--
-- TOC entry 3263 (class 2606 OID 16455)
-- Name: comment fk_comment_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fk_comment_user_id FOREIGN KEY (user_id) REFERENCES public."users"(id) NOT VALID;


--
-- TOC entry 3264 (class 2606 OID 16460)
-- Name: favorites fk_favorites_post_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fk_favorites_post_id FOREIGN KEY (post_id) REFERENCES public.post(id) NOT VALID;


--
-- TOC entry 3265 (class 2606 OID 16465)
-- Name: favorites fk_favorites_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.favorites
    ADD CONSTRAINT fk_favorites_user_id FOREIGN KEY (user_id) REFERENCES public."users"(id) NOT VALID;


--
-- TOC entry 3267 (class 2606 OID 16475)
-- Name: post_additional_content fk_post_additional_content_comment_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post_additional_content
    ADD CONSTRAINT fk_post_additional_content_comment_id FOREIGN KEY (comment_id) REFERENCES public.comment(id) NOT VALID;

ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_post_category_id FOREIGN KEY (category_id) REFERENCES public.category(id) NOT VALID;

ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_post_userId FOREIGN KEY (user_id) REFERENCES public.users(id) NOT VALID;


--
-- TOC entry 3266 (class 2606 OID 16470)
-- Name: post_additional_content fk_post_additional_content_post_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post_additional_content
    ADD CONSTRAINT fk_post_additional_content_post_id FOREIGN KEY (post_id) REFERENCES public.post(id) NOT VALID;


--
-- TOC entry 3268 (class 2606 OID 16480)
-- Name: reply fk_reply_comment; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reply
    ADD CONSTRAINT fk_reply_comment FOREIGN KEY (comment_id) REFERENCES public.comment(id) NOT VALID;


--
-- TOC entry 3269 (class 2606 OID 16485)
-- Name: reply fk_reply_reply_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reply
    ADD CONSTRAINT fk_reply_reply_id FOREIGN KEY (reply_id) REFERENCES public.comment(id) NOT VALID;


-- Completed on 2024-03-07 15:05:35 CET

--
-- PostgreSQL database dump complete
--