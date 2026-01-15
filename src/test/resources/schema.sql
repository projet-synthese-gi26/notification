-- Supprime les tables si elles existent pour un redémarrage propre en développement
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS email_template;
DROP TABLE IF EXISTS sms_template;
DROP TABLE IF EXISTS pull_template;
DROP TABLE IF EXISTS whatsapp_template;
DROP TABLE IF EXISTS email_sender;
DROP TABLE IF EXISTS sms_sender;
DROP TABLE IF EXISTS whatsapp_sender;
DROP TABLE IF EXISTS service_app;


-- Table pour les applications de service
CREATE TABLE service_app (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    token UUID NOT NULL UNIQUE
);

-- Table pour les configurations d'envoi d'emails
CREATE TABLE email_sender (
    email_sender_id INT AUTO_INCREMENT PRIMARY KEY,
    server_host VARCHAR(255),
    server_port VARCHAR(10),
    username VARCHAR(255),
    password VARCHAR(255),
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);

-- Table pour les configurations d'envoi de SMS
CREATE TABLE sms_sender (
    sms_sender_id INT AUTO_INCREMENT PRIMARY KEY,
    server_host VARCHAR(255),
    server_port VARCHAR(10),
    token VARCHAR(255),
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);

-- Table pour les configurations d'envoi de Whatsapp
CREATE TABLE whatsapp_sender (
    whatsapp_sender_id INT AUTO_INCREMENT PRIMARY KEY,
    id_instance VARCHAR(255),
    api_token_instance VARCHAR(255),
    api_url VARCHAR(255),
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);

-- Table pour les templates d'emails
CREATE TABLE email_template (
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    from_email VARCHAR(255),
    subject VARCHAR(255),
    body_html TEXT,
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);

-- Table pour les templates de whatsapp
CREATE TABLE whatsapp_template (
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    body TEXT,
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);

-- Table pour les notifications envoyées
CREATE TABLE notification (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id UUID,
    template_id INTEGER NOT NULL,
    notification_type VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMP,
    data CLOB,
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);

-- NOTE: Les tables pour sms_template et pull_template peuvent être ajoutées sur le même modèle.
CREATE TABLE sms_template (
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    message TEXT,
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);

CREATE TABLE pull_template (
    template_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    message TEXT,
    service_app_id INT,
    FOREIGN KEY (service_app_id) REFERENCES service_app(service_id)
);