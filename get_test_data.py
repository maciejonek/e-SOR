import pandas as pd

### Get sample for request operation to model API

df = pd.read_csv('test_data.csv')
df.reset_index(drop=True, inplace=True)
df.head()

df['Row_Sum'] = df.drop(columns=['esi', 'age', 'n_surgeries']).sum(axis=1)

#filtered_df = df[df['Row_Sum'] == 4]
filtered_df = df[df['esi'] == 1]

random_sample = filtered_df.sample(n=1)

selected_columns = ['esi', 'age', 'n_surgeries', 'Row_Sum'] + [col for col in random_sample.columns if random_sample[col].eq(1).any() and col not in ['esi', 'age', 'n_surgeries', 'Row_Sum']]

random_sample = random_sample[selected_columns]

random_sample = random_sample.drop(columns=['Row_Sum'])

json_data = random_sample.to_dict(orient='records')[0]

import json
with open('sample.json', 'w') as json_file:
    json.dump(json_data, json_file)