PGDMP                  	    |            oms     16.4 (Ubuntu 16.4-1.pgdg24.04+2)     17.0 (Ubuntu 17.0-1.pgdg24.04+1)     }           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            ~           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    25378    oms    DATABASE     o   CREATE DATABASE oms WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';
    DROP DATABASE oms;
                     postgres    false            �            1259    25403    admin    TABLE     -  CREATE TABLE public.admin (
    id integer NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    nom character varying(255),
    prenom character varying(255),
    role character varying(255) NOT NULL,
    admin_type character varying(255) NOT NULL
);
    DROP TABLE public.admin;
       public         heap r       postgres    false            �            1259    25410    client    TABLE     G  CREATE TABLE public.client (
    id integer NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    nom character varying(255),
    prenom character varying(255),
    role character varying(255) NOT NULL,
    adresse character varying(255),
    moyen_paiement double precision
);
    DROP TABLE public.client;
       public         heap r       postgres    false            �            1259    25402    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public               postgres    false            �            1259    25380    products    TABLE     �   CREATE TABLE public.products (
    id bigint NOT NULL,
    description character varying(500),
    name character varying(100) NOT NULL,
    price double precision NOT NULL,
    stock integer NOT NULL
);
    DROP TABLE public.products;
       public         heap r       postgres    false            �            1259    25379    products_id_seq    SEQUENCE     x   CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.products_id_seq;
       public               postgres    false    216            �           0    0    products_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;
          public               postgres    false    215            �           2604    25383    products id    DEFAULT     j   ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);
 :   ALTER TABLE public.products ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    215    216    216            y          0    25403    admin 
   TABLE DATA           S   COPY public.admin (id, email, password, nom, prenom, role, admin_type) FROM stdin;
    public               postgres    false    218   �       z          0    25410    client 
   TABLE DATA           a   COPY public.client (id, email, password, nom, prenom, role, adresse, moyen_paiement) FROM stdin;
    public               postgres    false    219   �       w          0    25380    products 
   TABLE DATA           G   COPY public.products (id, description, name, price, stock) FROM stdin;
    public               postgres    false    216   �       �           0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 14, true);
          public               postgres    false    217            �           0    0    products_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.products_id_seq', 47, true);
          public               postgres    false    215            �           2606    25409    admin admin_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.admin DROP CONSTRAINT admin_pkey;
       public                 postgres    false    218            �           2606    25416    client client_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public                 postgres    false    219            �           2606    25387    products products_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pkey;
       public                 postgres    false    216            �           2606    25420 #   client uk_bfgjs3fem0hmjhvih80158x29 
   CONSTRAINT     _   ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_bfgjs3fem0hmjhvih80158x29 UNIQUE (email);
 M   ALTER TABLE ONLY public.client DROP CONSTRAINT uk_bfgjs3fem0hmjhvih80158x29;
       public                 postgres    false    219            �           2606    25418 "   admin uk_c0r9atamxvbhjjvy5j8da1kam 
   CONSTRAINT     ^   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT uk_c0r9atamxvbhjjvy5j8da1kam UNIQUE (email);
 L   ALTER TABLE ONLY public.admin DROP CONSTRAINT uk_c0r9atamxvbhjjvy5j8da1kam;
       public                 postgres    false    218            y   �   x�u�Mo�0 ��3��պ��۔q`"�%K���� 5��/s����N���G�)�e�(4-84ڰ&Ԃc�]^���Vy&Z��g}� р'�K6�V�U�0$t���J��Ǉ�q{���%1�ѝ���Fw렄M��Q�t1Y�Ǡa�G6t���`�!"�s��4w��<�����$}�6o��%;V�b�B�-�%�(D>�w�>�)��Q��%���)&B�����[�_LM˚Ru��C}�i~�o�      z   �   x�e���0E��;�#	
ع�X���(c�&"PP�|���Y�[��j���z�!	/2�4�yxY�7E��ς�e�Xҷ��q|�Ơ��tH�'$q9�r{}_}�R$�Up�2��?C�C�]�.�.܁�k[��)y
B�a<�2mU��Y��,�q֤T{��FN���R
��	?!f1�	���`D�      w   �   x�m�M� ���N�	�#�������ƴQ�x��6ʢ�7�f�Ҹ�#�!���B��:&&��O8�T�|NÒ�`k��4x2G9d�6�QvN��$�_��uiΡ)���>��6#��`$���?_g���)�߶}��7��[����}M�     