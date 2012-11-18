-- KEYS = ( idCounter )
-- ARGV = ( objPrefix, isNewHKey )
idCounter, objPrefix, isNewHKey = KEYS[1], ARGV[1], ARGV[2]

newId = "tmp:" .. redis.call( 'incr', idCounter )
redis.call( 'hset', objPrefix + newId, isNewHKey, true )
return newId