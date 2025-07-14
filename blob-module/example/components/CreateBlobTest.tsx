import { useEffect, useState } from "react";
import { Text, View, StyleSheet } from "react-native";
import { ExpoBlob as Blob } from "blob-module";

const blob0 = new Blob(["d", "ed", "fed"])
const blob = new Blob(["a", "bbb", blob0, new Blob(["c", new Blob(["h", new Blob(["uuuu", "UUUUU", new Blob(["b"])])])])], {
	type: "test/plain",
	endings: "native",
});

export function CreateBlobTestComponent() {
	const [blobText, setBlobText] = useState<string | null>(null);

	
	// const blob = new Blob([new Blob(), new Blob()]);
	// const blob = new Blob()
	// const blob = new Blob(1)
	// const blob = new Blob(["222e1fsfsa", "agagaeggahjkabe2"])


	// const blob = new Blob();
	useEffect(() => {
		blob?.text().then((text) => {
			setBlobText(text);
		});
	}, [])


	return (
		<View style={styles.container}>
			<Text>Size: {blob?.size}</Text>
			<Text>Type: {blob?.type}</Text>
			<Text>Text: {blobText}</Text>
			<Text>{blob?.test()}</Text>
			<Text></Text>
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
