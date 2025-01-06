from flask import Flask, request, jsonify
import pandas as pd
from autogluon.tabular import TabularPredictor

### Simple flask api for POST request only

app = Flask(__name__)

model_path = "path"
model = TabularPredictor.load(model_path)

column_names = pd.read_csv('column_names.csv').columns.tolist()
dataset = pd.DataFrame(columns=column_names)

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    
    for col in column_names:
        if col not in data:
            data[col] = 0

    new_data = pd.DataFrame([data])

    new_data = new_data[column_names]
    
    print(new_data.head())
    prediction = model.predict(new_data)
    
    return jsonify({'prediction': prediction.item()})


if __name__ == '__main__':
    app.run(debug=True)
