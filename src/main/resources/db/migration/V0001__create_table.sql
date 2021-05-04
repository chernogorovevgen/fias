CREATE TABLE IF NOT EXISTS address_object
(
    id              TEXT NOT NULL PRIMARY KEY,
    object_id       TEXT,
    object_guid     TEXT,
    change_id       TEXT,
    name            TEXT,
    type_name       TEXT,
    level           TEXT,
    prev_id         TEXT,
    next_id         TEXT,
    update_date     TEXT,
    start_date      TEXT,
    end_date        TEXT,
    is_actual       TEXT,
    is_active       TEXT

    );

COMMENT ON TABLE address_object  IS '';
COMMENT ON COLUMN address_object.id IS '';
COMMENT ON COLUMN address_object.object_id  IS '';
COMMENT ON COLUMN address_object.object_guid IS '';
COMMENT ON COLUMN address_object.change_id IS '';
COMMENT ON COLUMN address_object.name IS '';
COMMENT ON COLUMN address_object.level  IS '';
COMMENT ON COLUMN address_object.prev_id IS '';
COMMENT ON COLUMN address_object.next_id IS '';
COMMENT ON COLUMN address_object.update_date IS '';
COMMENT ON COLUMN address_object.start_date IS '';
COMMENT ON COLUMN address_object.end_date IS '';
COMMENT ON COLUMN address_object.is_actual IS '';
COMMENT ON COLUMN address_object.is_active IS '';
