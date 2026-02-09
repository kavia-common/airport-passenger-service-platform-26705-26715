-- Flyway migration V3: flight_status_service specific tables
-- Shared V1 already creates `flights` and `flight_status_cache`.

CREATE TABLE IF NOT EXISTS gates (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  gate_code varchar(20) NOT NULL,
  terminal varchar(20),
  is_active boolean NOT NULL DEFAULT true,
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_gates_gate_code UNIQUE (gate_code)
);

CREATE TABLE IF NOT EXISTS baggage_belts (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  belt_code varchar(20) NOT NULL,
  terminal varchar(20),
  is_active boolean NOT NULL DEFAULT true,
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_baggage_belts_belt_code UNIQUE (belt_code)
);

CREATE INDEX IF NOT EXISTS idx_gates_active
  ON gates (is_active);

CREATE INDEX IF NOT EXISTS idx_baggage_belts_active
  ON baggage_belts (is_active);
