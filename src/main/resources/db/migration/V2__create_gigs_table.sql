-- V2__create_gigs_table.sql
CREATE TABLE gigs (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(255) NOT NULL,
    location_city VARCHAR(255) NOT NULL,
    budget_min NUMERIC,
    budget_max NUMERIC,
    creator_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_gigs_creator FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_gigs_category ON gigs(category);
CREATE INDEX idx_gigs_location_city ON gigs(location_city);