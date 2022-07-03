ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART WITH 3;
INSERT INTO "PUBLIC"."WEBSITE_USER"("ID", "GOOGLE_ID", "FACEBOOK_ID", "USER_EMAIL", "ATTRIBUTES") VALUES

    (2, '107207475118767146327', NULL, 'thomas.schuehly.mibi@gmail.com', '{"sub":"107207475118767146327","email_verified":true,"iss":"https://accounts.google.com","given_name":"Thomas","locale":"de","picture":"https://lh3.googleusercontent.com/a-/AOh14GiMm7UAP7Ey8YJRk075irQl__XZELo4InkSXfjDmw=s96-c","name":"Thomas","email":"thomas.schuehly.mibi@gmail.com"}');

INSERT INTO "PUBLIC"."WEBSITE_USER_AUTHORITIES"("WEBSITE_USER_ID", "AUTHORITIES") VALUES

                                                                                      (2, 'SCOPE_https://www.googleapis.com/auth/userinfo.email'),

                                                                                      (2, 'ROLE_ADMIN'),

                                                                                      (2, 'SCOPE_openid'),

                                                                                      (2, 'ROLE_USER'),

                                                                                      (2, 'SCOPE_https://www.googleapis.com/auth/userinfo.profile');

INSERT INTO "PUBLIC"."WEDDING"("ID", "BUCKET_ID", "SUBDOMAIN") VALUES
    (2, 'c215e06f-e061-4a8a-8068-60d648cbfbd1', '7bb774f0-717f-408a-a0db-9d42a35375b8');
INSERT INTO "PUBLIC"."WEBSITE_USER_WEDDINGS"("WEBSITE_USER_ID", "WEDDINGS_ID") VALUES
    (2, 2);
