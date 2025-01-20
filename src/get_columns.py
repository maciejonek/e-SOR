import pandas as pd

data = pd.read_csv('new_data.csv')
data = data[0:0]
data.head()

data.to_json('column_names.json', index=False)
data.to_csv('column_names.csv', index=False)