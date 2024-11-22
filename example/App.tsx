import React from 'react';
import { StyleSheet, Text, View, TouchableOpacity, Alert } from 'react-native';
import { ActionSheet } from '@powerdesigninc/react-native-actionsheet';

const options = ['Cancel', 'From Gallery', 'From Camera'];
const App = () => {
  const callback = (index: number) => {
    Alert.alert(`index is ${index}`);
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          ActionSheet.showActionSheetWithOptions(
            {
              title: 'Title',
              message: 'message',
              options,
              destructiveButtonIndex: 1,
              cancelButtonIndex: 0,
            },
            callback,
          );
        }}>
        <Text>Show ActionSheet</Text>
      </TouchableOpacity>

      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          ActionSheet.showActionSheetWithOptions(
            {
              options,
              destructiveButtonIndex: 1,
              cancelButtonIndex: 0,
            },
            callback,
          );
        }}>
        <Text>no title and message</Text>
      </TouchableOpacity>

      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          ActionSheet.showActionSheetWithOptions(
            {
              options: ['Cancel', 'From Gallery', 'From Camera'],
              destructiveButtonIndex: 0,
              cancelButtonIndex: 0,
            },
            callback,
          );
        }}>
        <Text>cancel destructive</Text>
      </TouchableOpacity>

      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          ActionSheet.showActionSheetWithOptions(
            {
              options: ['Cancel', 'From Gallery', 'From Camera'],
              hideCancelButton: true,
            },
            callback,
          );
        }}>
        <Text>hideCancelButton = true</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  button: {
    marginBottom: 20,
    padding: 20,
    width: '80%',
    textAlign: 'center',
    backgroundColor: '#5fc4b7',
  },
});

export default App;
