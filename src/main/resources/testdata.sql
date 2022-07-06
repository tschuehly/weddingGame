ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART WITH 3;

insert into public.website_user (id, google_id, facebook_id, user_email, attributes) values (2, '107207475118767146327', null, 'thomas.schuehly.mibi@gmail.com', '{"at_hash":"IYmMOJjdfjhD8BK-N7eQxQ","sub":"107207475118767146327","email_verified":true,"iss":"https://accounts.google.com","given_name":"Thomas","locale":"de","nonce":"Q2nBfUF-1O5P_C09R_mdsu5XVlp2D_TBsoG0XAMJBnY","picture":"https://lh3.googleusercontent.com/a-/AFdZucomhuHybawf5ihHf4489DIYPDBkn7jh4344tXI3YQ=s96-c","aud":["1003600519520-0islsn99ke3nnnd56q448e0utmf3d8uj.apps.googleusercontent.com"],"azp":"1003600519520-0islsn99ke3nnnd56q448e0utmf3d8uj.apps.googleusercontent.com","name":"Thomas","exp":1657121843.000000000,"iat":1657118243.000000000,"email":"thomas.schuehly.mibi@gmail.com"}');

insert into public.wedding (id, subfolder_id, subdomain) values (1, 'd9a85ff8-ad0c-4c84-bd08-633bdbaab626', 'e6aa7edd-a041-4b04-ac99-14c42f47350b');

insert into public.website_user_authorities (website_user_id, authorities) values (2, 'SCOPE_https://www.googleapis.com/auth/userinfo.email');
insert into public.website_user_authorities (website_user_id, authorities) values (2, 'ROLE_ADMIN');
insert into public.website_user_authorities (website_user_id, authorities) values (2, 'SCOPE_openid');
insert into public.website_user_authorities (website_user_id, authorities) values (2, 'ROLE_USER');
insert into public.website_user_authorities (website_user_id, authorities) values (2, 'SCOPE_https://www.googleapis.com/auth/userinfo.profile');
insert into public.website_user_weddings (website_user_id, weddings_id) values (2, 1);
