litterBox
Generic data collection and organization structure.

This will eventually exist as a content provider to allow apps to store data in flexible data structures.

Examples of how this would be used would be to allow a user to: Define new data structures describing some new 'thing' (such as a 'Book') Fully explain the attributes of that 'thing' and describe a collection of that 'thing' (such as a 'Library' of 'Book') as well. Provide a basic default UI to allow the user to manage those entities.

There would also be an API to allow other apps to poke into these structures.

Ambitious...but hopefully doable.

First version will use SQLite. Some future version will extent out to have the data stored somewhere 'in the cloud' (or on a remote server of some kind).

