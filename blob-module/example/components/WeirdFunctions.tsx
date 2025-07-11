import { StyleSheet, Text, View } from "react-native";
import { ExpoBlob as Blob } from "blob-module";

export default function WeirdFunctionsTest() {

    const blob : Blob = new Blob(["a", "b", "c"]);
    
    return (
        <View style={styles.container}>
            <Text>{blob.opt({
                endings: "native",
                type: 'react'
            })}</Text>

            <Text>{blob.either(1)}</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
    },
});
