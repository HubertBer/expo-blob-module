import { requireNativeModule } from "expo";
import { Blob } from "./BlobModule.types";

const NativeBlobModule: any = requireNativeModule("ExpoBlob");

export class ExpoBlob extends NativeBlobModule.Blob implements Blob {
	constructor(blob: string[], options?: BlobPropertyBag, other?: ExpoBlob) {
		super(blob, options, other)
	}
	
	// constructor(blob: string[]) {
	// 	super(blob)
	// }

	test(): string {
		return super.test();
	}

	opt(options : BlobPropertyBag) {
		return super.opt(options);
	}

	// constructor(blobParts?: any, options?: BlobPropertyBag) {
	// 	super(blobParts, options);
	// }

	// constructor(blob? : String | number | Blob[]) {
	// 	super(blob)
	// }

	// constructor() {
	// 	super()
	// }

	me(): Blob {
		return super.me()
	}

	slice(start?: number, end?: number, contentType?: string): Blob {
		return new ExpoBlob([], null, super.slice(start, end, contentType))
		return super.slice(start, end, contentType)
	}

	// either(sth : number | string): number | string {
	// 	return super.either(sth)
	// }

	// slice(start?: number, end?: number, contentType?: string): Blob {
	// 	const slicedBlob = super.slice(start, end, contentType);
	// 	const options: BlobPropertyBag = {
	// 		type: slicedBlob.type,
	// 		endings: slicedBlob.endings,
	// 	};
	// 	return new ExpoBlob(slicedBlob, options);
	// }

	async text(): Promise<string> {
		return Promise.resolve(super.text());
	}

	stream(): ReadableStream {
		const text = super.text();
		const encoder = new TextEncoder();
		const uint8 = encoder.encode(text);
		let offset = 0;
		return new ReadableStream<Uint8Array>({
			pull(controller) {
				if (offset < uint8.length) {
					controller.enqueue(uint8.subarray(offset));
					offset = uint8.length;
				} else {
					controller.close();
				}
			},
		});
	}
}
