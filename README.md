# jsonValueFetcher

##How to use##
1. Create a CSV with **two** columns, named `link` and `path` (headers **MUST** be there in the first line)
2. The `link` column must contain links to the API call (JSON only) for which you wish to retrieve the data (must have `http://` at the start)
3. The `path` column must contain the path to the value you wish to extract (**no lists or arrays**) in the JSONPath format (read more [here] (https://github.com/jayway/JsonPath))
4. Upload the CSV file and wait (no progress bar for now, except the console output).

###Example CSV###
```
link, path
http://api.foo.bar//apicall1, $.store.book[0].title
http://api.foo.bar//apicall2, $.store.book[0].title
http://api.foo.bar//apicall3, $.store.book[0].title
```

###Example output###
```
link, path
http://api.foo.bar//apicall1, $.store.book[0].title, The Great Gatsby
http://api.foo.bar//apicall2, $.store.book[0].title, Brothers Karamazov
http://api.foo.bar//apicall3, $.store.book[0].title, The Trial
```

## Roadmap##

- Concurrency
- Web app
- Option for selecting relevant CSV columns
- Working with variable number of columns
- Unit tests
- Cleaner code
- Charts? (for per-row fetches)


