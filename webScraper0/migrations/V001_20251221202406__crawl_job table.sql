CREATE TABLE crawl_job (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),              -- RUNNING, COMPLETED, FAILED
    max_pages INT,
    root_urls TEXT,
    keywords TEXT
);