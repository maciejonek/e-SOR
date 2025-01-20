from autogluon.tabular import TabularDataset, TabularPredictor
from sklearn.model_selection import train_test_split
import pandas as pd

target_column = 'esi'
df = pd.read_csv('new_data.csv')

### Data split into features and label
X = df.drop(columns=[target_column])
y = df[target_column]

### Split training and temp (validation + test)
X_train, X_temp, y_train, y_temp = train_test_split(X, y, test_size=0.3, random_state=42, stratify=y)

### Split temp into validation and test
X_val, X_test, y_val, y_test = train_test_split(X_temp, y_temp, test_size=0.5, random_state=42, stratify=y_temp)

test_data_combined = pd.concat([X_test, y_test], axis=1)

# Save the combined test data to a CSV file
test_data_combined.to_csv('test_data.csv', index=False)

### Check the shapes of the splits
print(f"Training set: {X_train.shape}, Validation set: {X_val.shape}, Test set: {X_test.shape}")

#### AUTOGLUON ####

### Prepare training, validation, and test sets
train_data = TabularDataset(pd.concat([X_train, y_train], axis=1))
val_data = TabularDataset(pd.concat([X_val, y_val], axis=1))
test_data = TabularDataset(pd.concat([X_test, y_test], axis=1))

### AutoGluon TabularPredictor
predictor = TabularPredictor(label=target_column,
                             problem_type = 'multiclass').fit(train_data,
                                                      #tuning_data=val_data,
                                                      presets=['good_quality_faster_inference_only_refit', 'optimize_for_deployment'],
                                                      time_limit=1200,
                                                      ag_args_fit={"ag.max_memory_usage_ratio": 1.2},
                                                      )
### Evaluate the model
performance = predictor.evaluate(test_data)
print("Performance on the test set:", performance)
