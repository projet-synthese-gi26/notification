-- liquibase formatted sql

-- changeset yowyob:001-initial-schema
-- comment: Initialisation du schéma de l'API de Notification

-- Table pour les applications de service
CREATE TABLE IF NOT EXISTS service_app (
    service_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    token UUID NOT NULL UNIQUE
);

-- Table pour les configurations d'envoi d'emails
CREATE TABLE IF NOT EXISTS email_sender (
    email_sender_id SERIAL PRIMARY KEY,
    server_host VARCHAR(255),
    server_port VARCHAR(10),
    username VARCHAR(255),
    password VARCHAR(255),
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Table pour les configurations d'envoi de SMS
CREATE TABLE IF NOT EXISTS sms_sender (
    sms_sender_id SERIAL PRIMARY KEY,
    server_host VARCHAR(255),
    server_port VARCHAR(10),
    token VARCHAR(255),
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Table pour les configurations d'envoi de Whatsapp
CREATE TABLE IF NOT EXISTS whatsapp_sender (
    whatsapp_sender_id SERIAL PRIMARY KEY,
    id_instance VARCHAR(255),
    api_token_instance VARCHAR(255),
    api_url VARCHAR(255),
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Table pour les templates d'emails
CREATE TABLE IF NOT EXISTS email_template (
    template_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    from_email VARCHAR(255),
    subject VARCHAR(255),
    body_html TEXT,
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Table pour les templates de whatsapp
CREATE TABLE IF NOT EXISTS whatsapp_template (
    template_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    body TEXT,
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Table pour les notifications envoyées
CREATE TABLE IF NOT EXISTS notification (
    notification_id SERIAL PRIMARY KEY,
    user_id UUID,
    template_id INTEGER NOT NULL,
    notification_type VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMP,
    data JSONB, 
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Table pour les configurations d'envoi de Push
CREATE TABLE IF NOT EXISTS push_sender (
    push_sender_id SERIAL PRIMARY KEY,
    service_account_json TEXT NOT NULL,
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Table pour les templates de Push
CREATE TABLE IF NOT EXISTS push_template (
    template_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    title VARCHAR(255),
    body TEXT,
    image_url VARCHAR(255),
    click_action TEXT,
    service_app_id INTEGER REFERENCES service_app(service_id)
);

-- Ajout des tables manquantes pour la cohérence de la TemplateFactory
CREATE TABLE IF NOT EXISTS sms_template (
    template_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    message TEXT,
    service_app_id INTEGER REFERENCES service_app(service_id)
);

CREATE TABLE IF NOT EXISTS pull_template (
    template_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    message TEXT,
    service_app_id INTEGER REFERENCES service_app(service_id)
);