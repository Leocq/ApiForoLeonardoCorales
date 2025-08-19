INSERT INTO courses(name) VALUES ('Java'), ('Spring'), ('SQL');
-- Usuario admin por defecto (password: admin)
INSERT INTO users(email, password_hash, role, display_name)
VALUES ('admin@foro.com', '$2a$10$kWSgY8U5K2o4o0x64jXwUO4X2Xz7m1y2K0Vw40j6k9QdQy1V0X3yG', 'ADMIN', 'Administrador');