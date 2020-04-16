import React from 'react';
import { StyleSheet, Text, View, TouchableOpacity, Alert } from 'react-native';
import { ActionSheet } from '@powerdesigninc/react-native-actionsheet';

const App = () => {
  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={styles.button}
        onPress={() => {
          ActionSheet.showActionSheetWithOptions(
            {
              title: 'Title',
              message: 'message',
              options: ['Cancel', 'From Gallery', 'From Camera'],
              destructiveButtonIndex: 1,
              cancelButtonIndex: 0,
            },
            index => {
              switch (index) {
                case 1: 
                  Alert.alert('From Camera clicked');
                  break;
                case 2: 
                  Alert.alert('From Camera clicked');
                  break;
                case 0: 
                  Alert.alert('onCancel');
                  break;
                default: 
                  Alert.alert('Default');                
              }
            },
          );
        }}>
        <Text>Show ActionSheet</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex:1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  button: {
    padding: 20,
    backgroundColor: "green"
  }
});

export default App;
