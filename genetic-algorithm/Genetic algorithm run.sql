CREATE TABLE GENETIC_ALGORITHM_RUN (
    GENETIC_ALGORITHM_input            VARCHAR2(4000),
    organisms number(*,0) check (organisms>0),
    generations number(*,0) check (generations>0),
    mutation_rate number check (mutation_rate between 0 and 1),
    start_time timestamp(9) with time zone,
    end_time timestamp(9) with time zone,
    number_of_generations_to_converge number(*,0), --check (number_of_generations_to_converge<=generations),
    converged number(1) check (converged in (0,1)),--boolean if it converged or not
    -- Additional columns for note and dates
    date_created           TIMESTAMP(9) WITH TIME ZONE DEFAULT systimestamp(9) NOT NULL,
    date_updated           TIMESTAMP(9) WITH TIME ZONE,
    date_created_or_updated TIMESTAMP(9) WITH TIME ZONE GENERATED ALWAYS AS ( coalesce(date_updated, date_created) ) VIRTUAL,
    GENETIC_ALGORITHM_RUN_id         RAW(16) DEFAULT sys_guid() PRIMARY KEY
);

-- Trigger to update date_updated for GENETIC_ALGORITHM_RUN
CREATE OR REPLACE TRIGGER set_date_updated_GENETIC_ALGORITHM_RUN
    BEFORE UPDATE ON GENETIC_ALGORITHM_RUN
    FOR EACH ROW
BEGIN
    :new.date_updated := systimestamp;
END;
/
