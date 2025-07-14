import { useState } from "react";
import { Text, View, StyleSheet } from "react-native";
import { ExpoBlob as Blob } from "blob-module";

export function SliceBlobTestComponent() {
	const [blobText, setBlobText] = useState<string | null>(null);
	const [slicedBlobText, setSlicedBlobText] = useState<string | null>(null);



	const blob = new Blob(["aaa", "bbbb", "ccccc", "dddddddddd"], {
		type: "test/plain",
		endings: "native",
	});
	const slicedBlob = blob.slice(0, 8);
	// const slicedBlob = blob.me()
	console.log("a",slicedBlob)

	slicedBlob.text().then((text) => {
		console.log({text})
		setSlicedBlobText(text);
	});


	blob.text().then((text) => {
		setBlobText(text);
	});

	return (
		<View style={styles.container}>
			<Text>Size: {blob?.size}</Text>
			<Text>Type: {blob?.type}</Text>
			<Text>Text before slice: {blobText}</Text>
			<Text>Text after slice [0-8]: {slicedBlobText}</Text>
			<Text>Slice size: {slicedBlob?.size}</Text>
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
