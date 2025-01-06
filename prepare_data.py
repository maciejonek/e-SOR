import pandas as pd
import pyreadr
import numpy as np

def NaN_check(df):
    for col in df.columns:
        nan_count = df[col].isna().sum()
        print(f"Column: {col}, NaN Count: {nan_count}")

def unique_num_check(num):
    for col in object_columns:
        if num in df[col].unique():
            print(f"Column: {col}, Unique Values: {df[col].unique()}")

### Read R data file 
result = pyreadr.read_r("5v_cleandf.rdata")
df = result['df']
df.head()

### Get info about column types 
df.info()

### Basic information for future data cleansing
df.select_dtypes(include=["float64"]).info()
df.select_dtypes(include=["int32"]).info()
df.select_dtypes(include=["category"]).info()
df.select_dtypes(include=["object"]).info()

df.select_dtypes(include=["float64"]).head()
df.select_dtypes(include=["int32"]).head()
df.select_dtypes(include=["category"]).head()
df.select_dtypes(include=["object"]).head()

### Print print columns with types to begin cleansing
column_types_dict = {col: str(df[col].dtype) for col in df.columns}
for (col, col_type) in column_types_dict.items():
    print(f"{col}: {col_type}")



### Change type of useful columns to avoid removing them
df['n_surgeries'] = df['n_surgeries'].fillna(0).astype("int8")
df['esi'] = df['esi'].astype("int8")

### Drop unnecessary columns
df = df.drop(df.select_dtypes(include=["float64"]).columns, axis=1)
df = df.drop(df.select_dtypes(include=["int32"]).columns, axis=1)

category_columns = df.select_dtypes(include=["category"]).columns.difference(['esi'])
df = df.drop(columns=category_columns)

### Drop NaN
NaN_check(df)
df.info() ### Number of rows before reduction
df = df.dropna()
df.info() ### Number of rows after reduction
NaN_check(df)


### Check unique values in each object column
object_columns = [col for col in df.columns if df[col].dtype == 'object']
for col in object_columns:
    unique_values = df[col].unique()
    print(f"Column: {col}")
    print(f"Unique Values: {unique_values}\n")

### Chenge object to int8
object_columns = df.select_dtypes(include=['object']).columns

for col in object_columns:
    df[col] = pd.to_numeric(df[col], errors='coerce').astype('int8', errors='ignore')

df.info()


### Check the impact of toe pain on the esi level
filtered_data = df[df['cc_other'].isin([1,2])]

for _, row in filtered_data.iterrows():
    print(f"cc_other: {row['cc_other']}, esi: {row['esi']}")

### Check the impact of chest pain on the esi level
filtered_data = df[df['cc_chestpain'].isin([1, 2])]

for _, row in filtered_data.iterrows():
    print(f"cc_chestpain: {row['cc_chestpain']}, esi: {row['esi']}")

unique_num_check(2)
unique_num_check(3)
unique_num_check(4)

print(df[~df['esi'].isin([0, 1, 2])]['cc_medicalproblem'].sum())
print(df[~df['esi'].isin([0, 1, 2])]['cc_other'].sum())

### Keep rows when cc_ther and cc_mediacalproblem is not affecting
df.info()
df = df[df['cc_other'] == 0]
df = df[df['cc_medicalproblem'] == 0]
df = df[~(df == 2).any(axis=1)]
df.info()

df.to_csv("new_data.csv", index=False)



### Correlation in prepared dataset

correlation_matrix = df.corr()

### Mask the diagonal in correlation matrix (always 1)
mask = np.triu(np.ones(correlation_matrix.shape), k=1)  # Upper triangular matrix (excluding diagonal)
masked_correlation_matrix = correlation_matrix * mask

### Flatten matrix and sort values
correlation_values = masked_correlation_matrix.stack().sort_values()

### Extract highest and lowest values
highest_corr_columns = correlation_values.tail(10)
lowest_corr_columns = correlation_values.head(10)

### Higher corr == lower priority (e.g. level 5 or color blue), 
### Lower corr == higher priority (e.g. level 1 or color red)
print(f"Highest correlation: {highest_corr_columns.index[0]} with value: {highest_corr_columns.values[0]}")
print(f"Lowest correlation: {lowest_corr_columns.index[0]} with value: {lowest_corr_columns.values[0]}")

