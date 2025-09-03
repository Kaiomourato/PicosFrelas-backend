-- V3__create_proposals_and_jobs_tables.sql
CREATE TABLE proposals (
    id UUID PRIMARY KEY,
    message TEXT,
    proposed_value NUMERIC,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    gig_id UUID NOT NULL,
    freelancer_id UUID NOT NULL,
    CONSTRAINT fk_proposals_gig FOREIGN KEY (gig_id) REFERENCES gigs(id) ON DELETE CASCADE,
    CONSTRAINT fk_proposals_freelancer FOREIGN KEY (freelancer_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE jobs (
    id UUID PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    started_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP WITH TIME ZONE,
    gig_id UUID NOT NULL UNIQUE,
    proposal_id UUID NOT NULL UNIQUE,
    contractor_id UUID NOT NULL,
    freelancer_id UUID NOT NULL,
    CONSTRAINT fk_jobs_gig FOREIGN KEY (gig_id) REFERENCES gigs(id) ON DELETE CASCADE,
    CONSTRAINT fk_jobs_proposal FOREIGN KEY (proposal_id) REFERENCES proposals(id) ON DELETE CASCADE,
    CONSTRAINT fk_jobs_contractor FOREIGN KEY (contractor_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_jobs_freelancer FOREIGN KEY (freelancer_id) REFERENCES users(id) ON DELETE CASCADE
);